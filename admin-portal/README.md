# CS Maps Ads - Admin Portal

Web-based administration portal for managing advertisements and viewing analytics.

---

## 🌐 Features

- **Dashboard** - Real-time statistics (impressions, clicks, CTR)
- **Manage Ads** - View all advertisements in a table
- **Create Ads** - Form to create new advertisements
- **Auto-refresh** - Stats and ads update automatically

---

## 🚀 Live Demo

**URL:** Will be available after Vercel deployment

---

## 📦 Local Development

### Prerequisites

- Web browser (Chrome, Firefox, Safari, Edge)
- Internet connection (to connect to API)

### Run Locally

1. **Navigate to admin-portal directory:**

```bash
cd admin-portal
```

2. **Open index.html in browser:**

```bash
# On Windows
start index.html

# On Mac
open index.html

# On Linux
xdg-open index.html
```

3. **Or use a local server:**

```bash
# Using Python
python -m http.server 8000

# Using Node.js
npx http-server

# Then open: http://localhost:8000
```

---

## 🔧 Configuration

### API Endpoint

The portal connects to the backend API at:

```javascript
const API_BASE_URL = 'https://cs-maps-ads-project.vercel.app';
```

To change the API endpoint, edit `app.js` line 2.

---

## 📊 Dashboard

Displays real-time statistics:

- **Total Impressions** - Number of times ads were displayed
- **Total Clicks** - Number of times ads were clicked
- **Click-Through Rate (CTR)** - Percentage of impressions that resulted in clicks
- **Active Ads** - Number of currently active advertisements

---

## 📢 Manage Ads

View all advertisements in a table with:

- Ad image thumbnail
- Title and description
- Link URL
- Impressions and clicks count
- Active/inactive status

Click "🔄 Refresh List" to reload ads.

---

## ➕ Create Ad

Form to create new advertisements:

**Required Fields:**
- Ad Title
- Description
- Image URL
- Link URL

**Optional Fields:**
- Location Targeting
- Active status (checkbox)

---

## 🚢 Deployment to Vercel

### Option 1: Via Vercel Dashboard

1. Go to [vercel.com](https://vercel.com)
2. Click "Add New Project"
3. Select your GitHub repository
4. Set **Root Directory** to `admin-portal`
5. Click "Deploy"

### Option 2: Via Vercel CLI

```bash
cd admin-portal
vercel --prod
```

---

## 🛠️ Tech Stack

- **HTML5** - Structure
- **CSS3** - Styling (responsive design)
- **JavaScript (ES6+)** - Functionality
- **Fetch API** - HTTP requests
- **Vercel** - Hosting

---

## 📱 Responsive Design

The portal is fully responsive and works on:

- Desktop (1200px+)
- Tablet (768px - 1199px)
- Mobile (< 768px)

---

## 🔐 Security Note

**Important:** This is a demo portal without authentication. For production use, add:

- User authentication (login/logout)
- Role-based access control
- API key protection
- HTTPS enforcement

---

## 📄 Files

```
admin-portal/
├── index.html     # Main HTML structure
├── style.css      # Styling and layout
├── app.js         # JavaScript logic
├── vercel.json    # Vercel deployment config
└── README.md      # This file
```

---

## 🐛 Troubleshooting

### No data loading

**Problem:** Stats or ads not loading

**Solutions:**
1. Check browser console for errors (F12)
2. Verify API is accessible: https://cs-maps-ads-project.vercel.app
3. Check internet connection
4. Try refreshing the page

### CORS errors

**Problem:** Cross-Origin Request Blocked

**Solution:** The backend API has CORS enabled. If you see this error, the backend might be down.

---

## 🤝 Support

For issues or questions:
- [GitHub Issues](https://github.com/JeFaZor/-cs-maps-ads-project/issues)
- [Main Documentation](../README.md)

---

**Built with ❤️ for advertisers**
