from flask import Blueprint, jsonify, request
from models.ad import Ad
from database import get_ads_collection
from bson import ObjectId
import random

# Create blueprint
ads_bp = Blueprint('ads', __name__)

# Get MongoDB collection
ads_collection = get_ads_collection()

@ads_bp.route('', methods=['GET'])
def get_all_ads():
    """Get all active ads"""
    ads = list(ads_collection.find({'active': True}))
    
    # Convert ObjectId to string
    for ad in ads:
        ad['ad_id'] = str(ad['_id'])
        ad['_id'] = str(ad['_id'])
    
    return jsonify({
        'status': 'success',
        'count': len(ads),
        'data': ads
    }), 200

@ads_bp.route('/random', methods=['GET'])
def get_random_ad():
    """Get a random active ad"""
    ads = list(ads_collection.find({'active': True}))
    
    if not ads:
        return jsonify({
            'status': 'error',
            'message': 'No ads available'
        }), 404
    
    random_ad = random.choice(ads)
    random_ad['ad_id'] = str(random_ad['_id'])
    random_ad['_id'] = str(random_ad['_id'])
    
    return jsonify({
        'status': 'success',
        'data': random_ad
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
    result = ads_collection.insert_one(ad.to_dict())
    
    ad_dict = ad.to_dict()
    ad_dict['ad_id'] = str(result.inserted_id)
    ad_dict['_id'] = str(result.inserted_id)
    
    return jsonify({
        'status': 'success',
        'message': 'Ad created successfully',
        'data': ad_dict
    }), 201

@ads_bp.route('/<ad_id>', methods=['GET'])
def get_ad_by_id(ad_id):
    """Get specific ad by ID"""
    try:
        ad = ads_collection.find_one({'_id': ObjectId(ad_id)})
    except:
        return jsonify({
            'status': 'error',
            'message': 'Invalid ad ID'
        }), 400
    
    if not ad:
        return jsonify({
            'status': 'error',
            'message': 'Ad not found'
        }), 404
    
    ad['ad_id'] = str(ad['_id'])
    ad['_id'] = str(ad['_id'])
    
    return jsonify({
        'status': 'success',
        'data': ad
    }), 200