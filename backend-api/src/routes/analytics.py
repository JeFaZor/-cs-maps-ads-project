from flask import Blueprint, jsonify, request
from datetime import datetime
from database import get_analytics_collection, get_ads_collection
from bson import ObjectId

# Create blueprint
analytics_bp = Blueprint('analytics', __name__)

# Get MongoDB collections
analytics_collection = get_analytics_collection()
ads_collection = get_ads_collection()

@analytics_bp.route('/impression', methods=['POST'])
def track_impression():
    """Track ad impression"""
    data = request.get_json()
    
    if 'ad_id' not in data:
        return jsonify({
            'status': 'error',
            'message': 'Missing ad_id'
        }), 400
    
    # Save impression event
    event = {
        'type': 'impression',
        'ad_id': data['ad_id'],
        'timestamp': datetime.utcnow(),
        'user_location': data.get('location')
    }
    
    analytics_collection.insert_one(event)
    
    # Update ad impressions count
    try:
        ads_collection.update_one(
            {'_id': ObjectId(data['ad_id'])},
            {'$inc': {'impressions': 1}}
        )
    except:
        pass
    
    return jsonify({
        'status': 'success',
        'message': 'Impression tracked'
    }), 201

@analytics_bp.route('/click', methods=['POST'])
def track_click():
    """Track ad click"""
    data = request.get_json()
    
    if 'ad_id' not in data:
        return jsonify({
            'status': 'error',
            'message': 'Missing ad_id'
        }), 400
    
    # Save click event
    event = {
        'type': 'click',
        'ad_id': data['ad_id'],
        'timestamp': datetime.utcnow(),
        'user_location': data.get('location')
    }
    
    analytics_collection.insert_one(event)
    
    # Update ad clicks count
    try:
        ads_collection.update_one(
            {'_id': ObjectId(data['ad_id'])},
            {'$inc': {'clicks': 1}}
        )
    except:
        pass
    
    return jsonify({
        'status': 'success',
        'message': 'Click tracked'
    }), 201

@analytics_bp.route('/stats', methods=['GET'])
def get_stats():
    """Get analytics statistics"""
    total_impressions = analytics_collection.count_documents({'type': 'impression'})
    total_clicks = analytics_collection.count_documents({'type': 'click'})
    
    return jsonify({
        'status': 'success',
        'data': {
            'total_impressions': total_impressions,
            'total_clicks': total_clicks,
            'ctr': round((total_clicks / total_impressions * 100), 2) if total_impressions > 0 else 0
        }
    }), 200