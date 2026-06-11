/* ============================================
   RESCUE MANAGEMENT SYSTEM — API Client
   Fetch-based client for Spring Boot REST APIs
   ============================================ */

const API_BASE = '/api';

const api = {
    // ── HTTP Helpers ──
    async request(endpoint, options = {}) {
        const url = `${API_BASE}${endpoint}`;
        const config = {
            headers: { 'Content-Type': 'application/json' },
            ...options,
        };

        try {
            const response = await fetch(url, config);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || `HTTP ${response.status}`);
            }
            const text = await response.text();
            return text ? JSON.parse(text) : null;
        } catch (error) {
            console.error(`API Error [${options.method || 'GET'} ${url}]:`, error);
            throw error;
        }
    },

    get(endpoint) {
        return this.request(endpoint);
    },

    post(endpoint, data) {
        return this.request(endpoint, { method: 'POST', body: JSON.stringify(data) });
    },

    put(endpoint, data) {
        return this.request(endpoint, { method: 'PUT', body: JSON.stringify(data) });
    },

    patch(endpoint, data) {
        return this.request(endpoint, { method: 'PATCH', body: JSON.stringify(data) });
    },

    delete(endpoint) {
        return this.request(endpoint, { method: 'DELETE' });
    },

    // ── Emergency Endpoints ──
    emergencies: {
        getAll() { return api.get('/emergencies'); },
        getActive() { return api.get('/emergencies/active'); },
        getById(id) { return api.get(`/emergencies/${id}`); },
        getByStatus(status) { return api.get(`/emergencies/status/${status}`); },
        getByTeam(teamId) { return api.get(`/emergencies/team/${teamId}`); },
        getStats() { return api.get('/emergencies/stats'); },
        report(data) { return api.post('/emergencies', data); },
        assign(id, teamId) { return api.put(`/emergencies/${id}/assign`, { teamId }); },
        updateStatus(id, status) { return api.patch(`/emergencies/${id}/status`, { status }); },
        delete(id) { return api.delete(`/emergencies/${id}`); },
    },

    // ── Team Endpoints ──
    teams: {
        getAll() { return api.get('/teams'); },
        getAvailable() { return api.get('/teams/available'); },
        getOnMission() { return api.get('/teams/on-mission'); },
        getById(id) { return api.get(`/teams/${id}`); },
        create(data) { return api.post('/teams', data); },
        update(id, data) { return api.put(`/teams/${id}`, data); },
        updateStatus(id, status) { return api.patch(`/teams/${id}/status`, { status }); },
        delete(id) { return api.delete(`/teams/${id}`); },
    },

    // ── User Endpoints ──
    users: {
        login(email, password) { return api.post('/users/login', { email, password }); },
        register(data) { return api.post('/users/register', data); },
        getAll() { return api.get('/users'); },
        getById(id) { return api.get(`/users/${id}`); },
        getByRole(role) { return api.get(`/users/role/${role}`); },
        update(id, data) { return api.put(`/users/${id}`, data); },
        delete(id) { return api.delete(`/users/${id}`); },
    },

    // ── Join Request Endpoints ──
    joinRequests: {
        create(data) { return api.post('/join-requests', data); },
        getByTeam(teamId) { return api.get(`/join-requests/team/${teamId}`); },
        getByUser(userId) { return api.get(`/join-requests/user/${userId}`); },
        updateStatus(id, status) { return api.put(`/join-requests/${id}/status`, { status }); }
    },

    // ── Team Request Endpoints ──
    teamRequests: {
        create(data) { return api.post('/team-requests', data); },
        getAll() { return api.get('/team-requests'); },
        updateStatus(id, status) { return api.put(`/team-requests/${id}/status`, { status }); }
    }
};

// ── Session Management ──
const session = {
    save(user) {
        sessionStorage.setItem('currentUser', JSON.stringify(user));
    },

    get() {
        const data = sessionStorage.getItem('currentUser');
        return data ? JSON.parse(data) : null;
    },

    clear() {
        sessionStorage.removeItem('currentUser');
    },

    isLoggedIn() {
        return !!this.get();
    },

    getRole() {
        const user = this.get();
        return user ? user.role : null;
    },

    requireAuth(allowedRoles = []) {
        const user = this.get();
        if (!user) {
            window.location.href = '/login.html';
            return null;
        }
        if (allowedRoles.length > 0 && !allowedRoles.includes(user.role)) {
            window.location.href = '/login.html';
            return null;
        }
        return user;
    }
};

// ── Toast Notifications ──
const toast = {
    container: null,

    init() {
        if (!this.container) {
            this.container = document.createElement('div');
            this.container.className = 'toast-container';
            document.body.appendChild(this.container);
        }
    },

    show(message, type = 'info', duration = 4000) {
        this.init();
        const icons = { success: '✅', error: '❌', info: 'ℹ️', warning: '⚠️' };
        const el = document.createElement('div');
        el.className = `toast toast-${type}`;
        el.innerHTML = `
            <span>${icons[type] || ''}</span>
            <span>${message}</span>
            <button class="toast-close" onclick="this.parentElement.remove()">✕</button>
        `;
        this.container.appendChild(el);
        setTimeout(() => {
            el.style.opacity = '0';
            el.style.transform = 'translateX(50px)';
            setTimeout(() => el.remove(), 300);
        }, duration);
    },

    success(msg) { this.show(msg, 'success'); },
    error(msg) { this.show(msg, 'error'); },
    info(msg) { this.show(msg, 'info'); },
    warning(msg) { this.show(msg, 'warning'); },
};

// ── Utility Functions ──
const utils = {
    // Format date/time
    formatDate(dateStr) {
        if (!dateStr) return 'N/A';
        const date = new Date(dateStr);
        return date.toLocaleDateString('en-US', {
            month: 'short', day: 'numeric', year: 'numeric'
        });
    },

    formatTime(dateStr) {
        if (!dateStr) return 'N/A';
        const date = new Date(dateStr);
        return date.toLocaleTimeString('en-US', {
            hour: '2-digit', minute: '2-digit'
        });
    },

    formatDateTime(dateStr) {
        if (!dateStr) return 'N/A';
        return `${this.formatDate(dateStr)} ${this.formatTime(dateStr)}`;
    },

    timeAgo(dateStr) {
        if (!dateStr) return 'N/A';
        const now = new Date();
        const date = new Date(dateStr);
        const seconds = Math.floor((now - date) / 1000);

        if (seconds < 60) return 'Just now';
        if (seconds < 3600) return `${Math.floor(seconds / 60)}m ago`;
        if (seconds < 86400) return `${Math.floor(seconds / 3600)}h ago`;
        return `${Math.floor(seconds / 86400)}d ago`;
    },

    // Disaster type icons
    disasterIcon(type) {
        const icons = {
            FLOOD: '🌊', EARTHQUAKE: '🏚️', FIRE: '🔥', STORM: '🌪️',
            LANDSLIDE: '⛰️', ACCIDENT: '🚗', BUILDING_COLLAPSE: '🏗️', OTHER: '⚠️'
        };
        return icons[type] || '⚠️';
    },

    // Severity badge class
    severityClass(severity) {
        return `badge-${(severity || '').toLowerCase()}`;
    },

    // Status badge class
    statusClass(status) {
        const map = {
            PENDING: 'badge-pending',
            IN_PROGRESS: 'badge-in-progress',
            COMPLETED: 'badge-completed'
        };
        return map[status] || 'badge-pending';
    },

    // Status display text
    statusText(status) {
        const map = {
            PENDING: 'Pending',
            IN_PROGRESS: 'In Progress',
            COMPLETED: 'Completed'
        };
        return map[status] || status;
    },

    // Animate counter
    animateCounter(element, target, duration = 1500) {
        let start = 0;
        const startTime = performance.now();

        function update(currentTime) {
            const elapsed = currentTime - startTime;
            const progress = Math.min(elapsed / duration, 1);
            const eased = 1 - Math.pow(1 - progress, 3); // ease-out cubic
            const current = Math.floor(eased * target);

            element.textContent = current;
            if (progress < 1) requestAnimationFrame(update);
        }

        requestAnimationFrame(update);
    },

    // Set up navbar for authenticated pages
    setupNavbar(user) {
        const userNameEl = document.getElementById('user-name');
        const userRoleEl = document.getElementById('user-role');
        const avatarEl = document.getElementById('user-avatar');

        if (userNameEl) userNameEl.textContent = user.name;
        if (userRoleEl) userRoleEl.textContent = user.role.replace('_', ' ');
        if (avatarEl) avatarEl.textContent = user.name.charAt(0).toUpperCase();
    },

    // Logout
    logout() {
        session.clear();
        window.location.href = '/login.html';
    }
};
