from flask import Blueprint, jsonify, request
from models.ad import Ad
import random

# Create blueprint
ads_bp = Blueprint('ads', __name__)

# Temporary in-memory storage (until we connect MongoDB)
ads_storage = []

@ads_bp.route('', methods=['GET'])
def get_all_ads():
    """Get all active ads"""
    active_ads = [ad.to_dict() for ad in ads_storage if ad.active]
    return jsonify({
        'status': 'success',
        'count': len(active_ads),
        'data': active_ads
    }), 200

@ads_bp.route('/random', methods=['GET'])
def get_random_ad():
    """Get a random active ad"""
    active_ads = [ad for ad in ads_storage if ad.active]
    
    if not active_ads:
        return jsonify({
            'status': 'error',
            'message': 'No ads available'
        }), 404
    
    random_ad = random.choice(active_ads)
    return jsonify({
        'status': 'success',
        'data': random_ad.to_dict()
    }), 200

@ads_bp.route('', methods=['POST'])
def create_ad():
    """Create a new ad"""
    data = request.get_json()
    
    # Validate required fields
    required_fields = ['title', 'description', 'image_url', 'link_url']
    for field in required_fields:
        if field not in data:
            return jsonify({
                'status': 'error',
                'message': f'Missing required field: {field}'
            }), 400
    
    # Create ad
    ad = Ad.from_dict(data)
    ad.ad_id = len(ads_storage) + 1  # Simple ID generation
    ads_storage.append(ad)
    
    return jsonify({
        'status': 'success',
        'message': 'Ad created successfully',
        'data': ad.to_dict()
    }), 201

@ads_bp.route('/<int:ad_id>', methods=['GET'])
def get_ad_by_id(ad_id):
    """Get specific ad by ID"""
    ad = next((ad for ad in ads_storage if ad.ad_id == ad_id), None)
    
    if not ad:
        return jsonify({
            'status': 'error',
            'message': 'Ad not found'
        }), 404
    
    return jsonify({
        'status': 'success',
        'data': ad.to_dict()
    }), 200