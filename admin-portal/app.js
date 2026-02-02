// API Configuration
const API_BASE_URL = 'https://cs-maps-ads-project.vercel.app';

// Tab Navigation
document.addEventListener('DOMContentLoaded', function() {
    // Setup tab navigation
    const navButtons = document.querySelectorAll('.nav-btn');
    const tabContents = document.querySelectorAll('.tab-content');

    navButtons.forEach(button => {
        button.addEventListener('click', () => {
            const tabId = button.getAttribute('data-tab');
            
            // Remove active class from all
            navButtons.forEach(btn => btn.classList.remove('active'));
            tabContents.forEach(tab => tab.classList.remove('active'));
            
            // Add active class to clicked
            button.classList.add('active');
            document.getElementById(tabId).classList.add('active');
            
            // Load data when switching tabs
            if (tabId === 'dashboard') {
                loadStats();
            } else if (tabId === 'ads') {
                loadAds();
            }
        });
    });

    // Load initial data
    loadStats();
    loadAds();

    // Setup form submissions
    const createForm = document.getElementById('createAdForm');
    createForm.addEventListener('submit', handleCreateAd);
    
    const editForm = document.getElementById('editAdForm');
    editForm.addEventListener('submit', handleEditAd);
});

// Load Statistics
async function loadStats() {
    try {
        // Get analytics stats
        const statsResponse = await fetch(`${API_BASE_URL}/api/analytics/stats`);
        const statsData = await statsResponse.json();
        
        // Get ads count
        const adsResponse = await fetch(`${API_BASE_URL}/api/ads`);
        const adsData = await adsResponse.json();
        
        // Update UI
        if (statsData.status === 'success') {
            document.getElementById('totalImpressions').textContent = 
                statsData.data.total_impressions.toLocaleString();
            document.getElementById('totalClicks').textContent = 
                statsData.data.total_clicks.toLocaleString();
            document.getElementById('ctr').textContent = 
                statsData.data.ctr + '%';
        }
        
        if (adsData.status === 'success') {
            document.getElementById('totalAds').textContent = adsData.count;
        }
        
    } catch (error) {
        console.error('Error loading stats:', error);
        showError('Failed to load statistics');
    }
}

// Load Ads
async function loadAds() {
    const tbody = document.getElementById('adsTableBody');
    tbody.innerHTML = '<tr><td colspan="8" class="loading">Loading ads...</td></tr>';
    
    try {
        const response = await fetch(`${API_BASE_URL}/api/ads`);
        const data = await response.json();
        
        if (data.status === 'success' && data.data.length > 0) {
            tbody.innerHTML = '';
            
            data.data.forEach(ad => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>
                        <img src="${ad.image_url}" alt="${ad.title}" class="ad-image" 
                             onerror="this.src='https://via.placeholder.com/80x60?text=No+Image'">
                    </td>
                    <td><strong>${escapeHtml(ad.title)}</strong></td>
                    <td>${escapeHtml(ad.description)}</td>
                    <td>
                        <a href="${ad.link_url}" target="_blank" class="ad-link">
                            ${truncateUrl(ad.link_url)}
                        </a>
                    </td>
                    <td>${ad.impressions.toLocaleString()}</td>
                    <td>${ad.clicks.toLocaleString()}</td>
                    <td>
                        <span class="status-badge ${ad.active ? 'status-active' : 'status-inactive'}">
                            ${ad.active ? 'Active' : 'Inactive'}
                        </span>
                    </td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-action btn-edit" onclick="openEditModal('${ad.ad_id}')" title="Edit">
                                ✏️
                            </button>
                            <button class="btn-action btn-delete" onclick="deleteAd('${ad.ad_id}')" title="Delete">
                                🗑️
                            </button>
                        </div>
                    </td>
                `;
                tbody.appendChild(row);
            });
        } else {
            tbody.innerHTML = '<tr><td colspan="8" class="loading">No ads found</td></tr>';
        }
        
    } catch (error) {
        console.error('Error loading ads:', error);
        tbody.innerHTML = '<tr><td colspan="8" class="loading">Error loading ads</td></tr>';
    }
}

// Handle Create Ad Form
async function handleCreateAd(event) {
    event.preventDefault();
    
    const messageDiv = document.getElementById('formMessage');
    messageDiv.className = 'form-message';
    messageDiv.style.display = 'none';
    
    // Get form data
    const formData = {
        title: document.getElementById('title').value.trim(),
        description: document.getElementById('description').value.trim(),
        image_url: document.getElementById('imageUrl').value.trim(),
        link_url: document.getElementById('linkUrl').value.trim(),
        location: document.getElementById('location').value.trim() || null,
        active: document.getElementById('active').checked
    };
    
    // Validate URLs
    if (!isValidUrl(formData.image_url)) {
        showFormMessage('formMessage', 'error', 'Invalid image URL');
        return;
    }
    
    if (!isValidUrl(formData.link_url)) {
        showFormMessage('formMessage', 'error', 'Invalid link URL');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/api/ads`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });
        
        const data = await response.json();
        
        if (data.status === 'success') {
            showFormMessage('formMessage', 'success', '✅ Advertisement created successfully!');
            document.getElementById('createAdForm').reset();
            
            // Reload stats and ads
            setTimeout(() => {
                loadStats();
                loadAds();
            }, 1000);
        } else {
            showFormMessage('formMessage', 'error', '❌ ' + (data.message || 'Failed to create ad'));
        }
        
    } catch (error) {
        console.error('Error creating ad:', error);
        showFormMessage('formMessage', 'error', '❌ Network error. Please try again.');
    }
}

// Open Edit Modal
async function openEditModal(adId) {
    const modal = document.getElementById('editModal');
    const messageDiv = document.getElementById('editFormMessage');
    messageDiv.style.display = 'none';
    
    try {
        // Fetch ad data
        const response = await fetch(`${API_BASE_URL}/api/ads/${adId}`);
        const data = await response.json();
        
        if (data.status === 'success') {
            const ad = data.data;
            
            // Populate form
            document.getElementById('editAdId').value = ad.ad_id;
            document.getElementById('editTitle').value = ad.title;
            document.getElementById('editDescription').value = ad.description;
            document.getElementById('editImageUrl').value = ad.image_url;
            document.getElementById('editLinkUrl').value = ad.link_url;
            document.getElementById('editLocation').value = ad.location || '';
            document.getElementById('editActive').checked = ad.active;
            
            // Show modal
            modal.style.display = 'block';
        } else {
            alert('Failed to load ad data');
        }
    } catch (error) {
        console.error('Error loading ad:', error);
        alert('Error loading ad data');
    }
}

// Close Edit Modal
function closeEditModal() {
    const modal = document.getElementById('editModal');
    modal.style.display = 'none';
}

// Handle Edit Ad Form
async function handleEditAd(event) {
    event.preventDefault();
    
    const messageDiv = document.getElementById('editFormMessage');
    messageDiv.className = 'form-message';
    messageDiv.style.display = 'none';
    
    const adId = document.getElementById('editAdId').value;
    
    // Get form data
    const formData = {
        title: document.getElementById('editTitle').value.trim(),
        description: document.getElementById('editDescription').value.trim(),
        image_url: document.getElementById('editImageUrl').value.trim(),
        link_url: document.getElementById('editLinkUrl').value.trim(),
        location: document.getElementById('editLocation').value.trim() || null,
        active: document.getElementById('editActive').checked
    };
    
    // Validate URLs
    if (!isValidUrl(formData.image_url)) {
        showFormMessage('editFormMessage', 'error', 'Invalid image URL');
        return;
    }
    
    if (!isValidUrl(formData.link_url)) {
        showFormMessage('editFormMessage', 'error', 'Invalid link URL');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/api/ads/${adId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });
        
        const data = await response.json();
        
        if (data.status === 'success') {
            showFormMessage('editFormMessage', 'success', '✅ Advertisement updated successfully!');
            
            // Reload ads after delay
            setTimeout(() => {
                closeEditModal();
                loadStats();
                loadAds();
            }, 1500);
        } else {
            showFormMessage('editFormMessage', 'error', '❌ ' + (data.message || 'Failed to update ad'));
        }
        
    } catch (error) {
        console.error('Error updating ad:', error);
        showFormMessage('editFormMessage', 'error', '❌ Network error. Please try again.');
    }
}

// Delete Ad
async function deleteAd(adId) {
    if (!confirm('Are you sure you want to delete this advertisement? This action cannot be undone.')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/api/ads/${adId}`, {
            method: 'DELETE'
        });
        
        const data = await response.json();
        
        if (data.status === 'success') {
            alert('✅ Advertisement deleted successfully!');
            loadStats();
            loadAds();
        } else {
            alert('❌ ' + (data.message || 'Failed to delete ad'));
        }
        
    } catch (error) {
        console.error('Error deleting ad:', error);
        alert('❌ Network error. Please try again.');
    }
}

// Show Form Message
function showFormMessage(elementId, type, message) {
    const messageDiv = document.getElementById(elementId);
    messageDiv.className = `form-message ${type}`;
    messageDiv.textContent = message;
    messageDiv.style.display = 'block';
    
    // Scroll to message
    messageDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

// Utility Functions
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function truncateUrl(url, maxLength = 40) {
    if (url.length <= maxLength) return url;
    return url.substring(0, maxLength) + '...';
}

function isValidUrl(string) {
    try {
        new URL(string);
        return true;
    } catch (_) {
        return false;
    }
}

function showError(message) {
    console.error(message);
}

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target == modal) {
        closeEditModal();
    }
}

// Auto-refresh stats every 30 seconds
setInterval(() => {
    const dashboardTab = document.getElementById('dashboard');
    if (dashboardTab.classList.contains('active')) {
        loadStats();
    }
}, 30000);

// Auto-refresh ads list every 60 seconds
setInterval(() => {
    const adsTab = document.getElementById('ads');
    if (adsTab.classList.contains('active')) {
        loadAds();
    }
}, 60000);