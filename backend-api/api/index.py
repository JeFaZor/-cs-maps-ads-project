from flask import Flask, jsonify, request
from flask_cors import CORS
from pymongo import MongoClient
from bson import ObjectId
import os
import random
from datetime import datetime

# Initialize Flask
app = Flask(__name__)
CORS(app)

# MongoDB connection
MONGODB_URI = os.getenv('MONGODB_URI')
client = MongoClient(MONGODB_URI)
db = client['csads']
ads_collection = db['ads']
analytics_collection = db['analytics']

# Health check
@app.route('/')
@app.route('/api')
def home():
    return jsonify({
        'status': 'success',
        'message': 'CS Maps Ads API is running with MongoDB',
        'version': '1.0.1'
    })

# Get all ads
@app.route('/api/ads', methods=['GET'])
def get_all_ads():
    ads = list(ads_collection.find({'active': True}))
    for ad in ads:
        ad['ad_id'] = str(ad['_id'])
        ad['_id'] = str(ad['_id'])
    return jsonify({'status': 'success', 'count': len(ads), 'data': ads}), 200

# Get random ad
@app.route('/api/ads/random', methods=['GET'])
def get_random_ad():
    ads = list(ads_collection.find({'active': True}))
    if not ads:
        return jsonify({'status': 'error', 'message': 'No ads available'}), 404
    ad = random.choice(ads)
    ad['ad_id'] = str(ad['_id'])
    ad['_id'] = str(ad['_id'])
    return jsonify({'status': 'success', 'data': ad}), 200

# Get specific ad by ID
@app.route('/api/ads/<ad_id>', methods=['GET'])
def get_ad_by_id(ad_id):
    try:
        ad = ads_collection.find_one({'_id': ObjectId(ad_id)})
        if not ad:
            return jsonify({'status': 'error', 'message': 'Ad not found'}), 404
        ad['ad_id'] = str(ad['_id'])
        ad['_id'] = str(ad['_id'])
        return jsonify({'status': 'success', 'data': ad}), 200
    except:
        return jsonify({'status': 'error', 'message': 'Invalid ad ID'}), 400

# Create ad
@app.route('/api/ads', methods=['POST'])
def create_ad():
    data = request.get_json()
    required = ['title', 'description', 'image_url', 'link_url']
    for field in required:
        if field not in data:
            return jsonify({'status': 'error', 'message': f'Missing: {field}'}), 400
    
    ad = {
        'title': data['title'],
        'description': data['description'],
        'image_url': data['image_url'],
        'link_url': data['link_url'],
        'location': data.get('location'),
        'active': data.get('active', True),
        'created_at': datetime.utcnow(),
        'impressions': 0,
        'clicks': 0
    }
    result = ads_collection.insert_one(ad)
    ad['ad_id'] = str(result.inserted_id)
    ad['_id'] = str(result.inserted_id)
    return jsonify({'status': 'success', 'message': 'Ad created', 'data': ad}), 201

# Update ad
@app.route('/api/ads/<ad_id>', methods=['PUT'])
def update_ad(ad_id):
    try:
        data = request.get_json()
        
        # Build update object
        update_data = {}
        if 'title' in data:
            update_data['title'] = data['title']
        if 'description' in data:
            update_data['description'] = data['description']
        if 'image_url' in data:
            update_data['image_url'] = data['image_url']
        if 'link_url' in data:
            update_data['link_url'] = data['link_url']
        if 'location' in data:
            update_data['location'] = data['location']
        if 'active' in data:
            update_data['active'] = data['active']
        
        if not update_data:
            return jsonify({'status': 'error', 'message': 'No fields to update'}), 400
        
        # Update in database
        result = ads_collection.update_one(
            {'_id': ObjectId(ad_id)},
            {'$set': update_data}
        )
        
        if result.matched_count == 0:
            return jsonify({'status': 'error', 'message': 'Ad not found'}), 404
        
        # Get updated ad
        ad = ads_collection.find_one({'_id': ObjectId(ad_id)})
        ad['ad_id'] = str(ad['_id'])
        ad['_id'] = str(ad['_id'])
        
        return jsonify({'status': 'success', 'message': 'Ad updated', 'data': ad}), 200
    
    except:
        return jsonify({'status': 'error', 'message': 'Invalid ad ID'}), 400

# Delete ad
@app.route('/api/ads/<ad_id>', methods=['DELETE'])
def delete_ad(ad_id):
    try:
        result = ads_collection.delete_one({'_id': ObjectId(ad_id)})
        
        if result.deleted_count == 0:
            return jsonify({'status': 'error', 'message': 'Ad not found'}), 404
        
        return jsonify({'status': 'success', 'message': 'Ad deleted'}), 200
    
    except:
        return jsonify({'status': 'error', 'message': 'Invalid ad ID'}), 400

# Track impression
@app.route('/api/analytics/impression', methods=['POST'])
def track_impression():
    data = request.get_json()
    if 'ad_id' not in data:
        return jsonify({'status': 'error', 'message': 'Missing ad_id'}), 400
    
    analytics_collection.insert_one({
        'type': 'impression',
        'ad_id': data['ad_id'],
        'timestamp': datetime.utcnow(),
        'user_location': data.get('location')
    })
    
    try:
        ads_collection.update_one({'_id': ObjectId(data['ad_id'])}, {'$inc': {'impressions': 1}})
    except:
        pass
    
    return jsonify({'status': 'success', 'message': 'Impression tracked'}), 201

# Track click
@app.route('/api/analytics/click', methods=['POST'])
def track_click():
    data = request.get_json()
    if 'ad_id' not in data:
        return jsonify({'status': 'error', 'message': 'Missing ad_id'}), 400
    
    analytics_collection.insert_one({
        'type': 'click',
        'ad_id': data['ad_id'],
        'timestamp': datetime.utcnow(),
        'user_location': data.get('location')
    })
    
    try:
        ads_collection.update_one({'_id': ObjectId(data['ad_id'])}, {'$inc': {'clicks': 1}})
    except:
        pass
    
    return jsonify({'status': 'success', 'message': 'Click tracked'}), 201

# Stats
@app.route('/api/analytics/stats', methods=['GET'])
def get_stats():
    impressions = analytics_collection.count_documents({'type': 'impression'})
    clicks = analytics_collection.count_documents({'type': 'click'})
    ctr = round((clicks / impressions * 100), 2) if impressions > 0 else 0
    return jsonify({'status': 'success', 'data': {'total_impressions': impressions, 'total_clicks': clicks, 'ctr': ctr}}), 200