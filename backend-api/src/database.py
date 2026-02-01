from pymongo import MongoClient
from dotenv import load_dotenv
import os

# Load environment variables
load_dotenv()

# MongoDB connection
MONGODB_URI = os.getenv('MONGODB_URI')
client = MongoClient(MONGODB_URI)

# Database and collections
db = client['csads']
ads_collection = db['ads']
analytics_collection = db['analytics']

def get_db():
    return db

def get_ads_collection():
    return ads_collection

def get_analytics_collection():
    return analytics_collection