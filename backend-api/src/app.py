from flask import Flask, jsonify
from flask_cors import CORS
from dotenv import load_dotenv
import os

# Load environment variables
load_dotenv()

# Initialize Flask app
app = Flask(__name__)
CORS(app)  # Enable CORS for Android app

# Import routes
from routes.ads import ads_bp
from routes.analytics import analytics_bp

# Register blueprints
app.register_blueprint(ads_bp, url_prefix='/api/ads')
app.register_blueprint(analytics_bp, url_prefix='/api/analytics')

# Health check endpoint
@app.route('/')
def home():
    return jsonify({
        'status': 'success',
        'message': 'CS Maps Ads API is running',
        'version': '1.0.0'
    })

# Run the app
if __name__ == '__main__':
    port = int(os.getenv('PORT', 5000))
    app.run(debug=True, host='0.0.0.0', port=port)