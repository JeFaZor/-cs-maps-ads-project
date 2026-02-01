from flask import Flask, jsonify
from flask_cors import CORS
import os
import sys

# Add src to path
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))

from database import get_db
from routes.ads import ads_bp
from routes.analytics import analytics_bp

# Initialize Flask app
app = Flask(__name__)
CORS(app)

# Test MongoDB connection
try:
    db = get_db()
    db.command('ping')
    print("✅ Connected to MongoDB!")
except Exception as e:
    print(f"❌ MongoDB error: {e}")

# Register blueprints
app.register_blueprint(ads_bp, url_prefix='/api/ads')
app.register_blueprint(analytics_bp, url_prefix='/api/analytics')

# Health check
@app.route('/')
@app.route('/api')
def home():
    return jsonify({
        'status': 'success',
        'message': 'CS Maps Ads API is running with MongoDB',
        'version': '1.0.1'
    })