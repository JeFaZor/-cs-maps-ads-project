from datetime import datetime

class Ad:
    """Ad model for MongoDB"""
    
    def __init__(self, title, description, image_url, link_url, 
                 location=None, active=True, ad_id=None):
        self.ad_id = ad_id
        self.title = title
        self.description = description
        self.image_url = image_url
        self.link_url = link_url
        self.location = location  # Geographic targeting (optional)
        self.active = active
        self.created_at = datetime.utcnow()
        self.impressions = 0
        self.clicks = 0
    
    def to_dict(self):
        """Convert ad to dictionary"""
        return {
            'ad_id': self.ad_id,
            'title': self.title,
            'description': self.description,
            'image_url': self.image_url,
            'link_url': self.link_url,
            'location': self.location,
            'active': self.active,
            'created_at': self.created_at,
            'impressions': self.impressions,
            'clicks': self.clicks
        }
    
    @staticmethod
    def from_dict(data):
        """Create ad from dictionary"""
        return Ad(
            title=data.get('title'),
            description=data.get('description'),
            image_url=data.get('image_url'),
            link_url=data.get('link_url'),
            location=data.get('location'),
            active=data.get('active', True),
            ad_id=data.get('ad_id')
        )