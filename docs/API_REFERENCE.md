# API Reference

Complete REST API documentation for CS Maps Ads backend service.

---

## Base URL

```
https://cs-maps-ads-project.vercel.app
```

---

## Endpoints

### Health Check

Check if API is running.

```http
GET /
```

**Response:**

```json
{
  "status": "success",
  "message": "CS Maps Ads API is running with MongoDB",
  "version": "1.0.1"
}
```

---

### Get All Ads

Retrieve all active advertisements.

```http
GET /api/ads
```

**Response:**

```json
{
  "status": "success",
  "count": 3,
  "data": [
    {
      "ad_id": "697f44579725d5a14ec59d24",
      "_id": "697f44579725d5a14ec59d24",
      "title": "Gaming Mouse Sale",
      "description": "50% off on all gaming mice!",
      "image_url": "https://picsum.photos/400/300",
      "link_url": "https://example.com/gaming-mouse",
      "location": null,
      "active": true,
      "created_at": "Sun, 01 Feb 2026 12:17:27 GMT",
      "impressions": 10,
      "clicks": 1
    }
  ]
}
```

---

### Get Random Ad

Get a random active advertisement.

```http
GET /api/ads/random
```

**Response:**

```json
{
  "status": "success",
  "data": {
    "ad_id": "697f44579725d5a14ec59d24",
    "_id": "697f44579725d5a14ec59d24",
    "title": "Gaming Mouse Sale",
    "description": "50% off on all gaming mice!",
    "image_url": "https://picsum.photos/400/300",
    "link_url": "https://example.com/gaming-mouse",
    "location": null,
    "active": true,
    "impressions": 10,
    "clicks": 1
  }
}
```

**Error Response (No ads):**

```json
{
  "status": "error",
  "message": "No ads available"
}
```

Status Code: `404`

---

### Create Ad

Create a new advertisement.

```http
POST /api/ads
Content-Type: application/json
```

**Request Body:**

```json
{
  "title": "Summer Sale",
  "description": "Get 30% off all items",
  "image_url": "https://example.com/image.jpg",
  "link_url": "https://example.com/sale",
  "location": "US",
  "active": true
}
```

**Required Fields:**
- `title` (string)
- `description` (string)
- `image_url` (string)
- `link_url` (string)

**Optional Fields:**
- `location` (string) - Geographic targeting
- `active` (boolean) - Default: true

**Response:**

```json
{
  "status": "success",
  "message": "Ad created",
  "data": {
    "ad_id": "697f48d49725d5a14ec59d2f",
    "_id": "697f48d49725d5a14ec59d2f",
    "title": "Summer Sale",
    "description": "Get 30% off all items",
    "image_url": "https://example.com/image.jpg",
    "link_url": "https://example.com/sale",
    "location": "US",
    "active": true,
    "created_at": "Mon, 02 Feb 2026 10:00:00 GMT",
    "impressions": 0,
    "clicks": 0
  }
}
```

Status Code: `201`

**Error Response (Missing field):**

```json
{
  "status": "error",
  "message": "Missing: title"
}
```

Status Code: `400`

---

### Track Impression

Record when an ad is displayed.

```http
POST /api/analytics/impression
Content-Type: application/json
```

**Request Body:**

```json
{
  "ad_id": "697f44579725d5a14ec59d24",
  "location": "US"
}
```

**Required Fields:**
- `ad_id` (string)

**Optional Fields:**
- `location` (string)

**Response:**

```json
{
  "status": "success",
  "message": "Impression tracked"
}
```

Status Code: `201`

---

### Track Click

Record when an ad is clicked.

```http
POST /api/analytics/click
Content-Type: application/json
```

**Request Body:**

```json
{
  "ad_id": "697f44579725d5a14ec59d24",
  "location": "US"
}
```

**Required Fields:**
- `ad_id` (string)

**Optional Fields:**
- `location` (string)

**Response:**

```json
{
  "status": "success",
  "message": "Click tracked"
}
```

Status Code: `201`

---

### Get Analytics Stats

Get overall analytics statistics.

```http
GET /api/analytics/stats
```

**Response:**

```json
{
  "status": "success",
  "data": {
    "total_impressions": 15,
    "total_clicks": 2,
    "ctr": 13.33
  }
}
```

**Fields:**
- `total_impressions` - Total ad views
- `total_clicks` - Total ad clicks
- `ctr` - Click-through rate (percentage)

---

## Data Models

### Ad Object

```json
{
  "ad_id": "string",           // Unique identifier
  "_id": "string",             // MongoDB ObjectId
  "title": "string",           // Ad headline
  "description": "string",     // Ad description
  "image_url": "string",       // Image URL
  "link_url": "string",        // Click destination
  "location": "string|null",   // Geographic target
  "active": boolean,           // Ad status
  "created_at": "datetime",    // Creation timestamp
  "impressions": number,       // View count
  "clicks": number             // Click count
}
```

---

## Error Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request (missing fields) |
| 404 | Not Found |
| 500 | Internal Server Error |

---

## Rate Limiting

No rate limiting currently implemented.

---

## CORS

CORS is enabled for all origins.

---

## Examples

### cURL Examples

**Get random ad:**

```bash
curl https://cs-maps-ads-project.vercel.app/api/ads/random
```

**Create ad:**

```bash
curl -X POST https://cs-maps-ads-project.vercel.app/api/ads \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Product",
    "description": "Check out our latest product",
    "image_url": "https://example.com/image.jpg",
    "link_url": "https://example.com/product"
  }'
```

**Track impression:**

```bash
curl -X POST https://cs-maps-ads-project.vercel.app/api/analytics/impression \
  -H "Content-Type: application/json" \
  -d '{"ad_id": "697f44579725d5a14ec59d24"}'
```

---

### JavaScript Examples

**Fetch random ad:**

```javascript
fetch('https://cs-maps-ads-project.vercel.app/api/ads/random')
  .then(response => response.json())
  .then(data => {
    console.log('Ad:', data.data);
  });
```

**Track click:**

```javascript
fetch('https://cs-maps-ads-project.vercel.app/api/analytics/click', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    ad_id: '697f44579725d5a14ec59d24'
  })
});
```

---

### Python Examples

**Get all ads:**

```python
import requests

response = requests.get('https://cs-maps-ads-project.vercel.app/api/ads')
data = response.json()
print(f"Found {data['count']} ads")
```

**Create ad:**

```python
import requests

ad_data = {
    "title": "Special Offer",
    "description": "Limited time deal",
    "image_url": "https://example.com/image.jpg",
    "link_url": "https://example.com/offer"
}

response = requests.post(
    'https://cs-maps-ads-project.vercel.app/api/ads',
    json=ad_data
)
print(response.json())
```

---

## Database

**Technology:** MongoDB Atlas

**Collections:**
- `ads` - Advertisement data
- `analytics` - Impression/click events

---

## Support

For API issues or questions:
- [GitHub Issues](https://github.com/JeFaZor/-cs-maps-ads-project/issues)
- [Main Documentation](../README.md)
