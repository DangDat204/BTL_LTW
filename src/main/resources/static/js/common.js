window.parseJwt = function(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
            atob(base64)
                .split('')
                .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                .join('')
        );
        return JSON.parse(jsonPayload);
    } catch (e) {
        console.error('Error parsing JWT:', e);
        return null;
    }
};

window.logout = function() {
    localStorage.removeItem('jwtToken');
    window.location.href = '/user/login';
};

window.navigateToAddRoom = function() {
    const token = localStorage.getItem('jwtToken');
    const payload = window.parseJwt(token);
    if (!token || !payload || (payload.exp && payload.exp < Math.floor(Date.now() / 1000))) {
        alert('Vui lòng đăng nhập lại!');
        window.logout();
        return;
    }
    fetch('http://localhost:8080/rooms/addRoom', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('Không thể truy cập trang thêm phòng');
            }
        })
        .then(data => {
            document.open();
            document.write(data);
            document.close();
        })
        .catch(error => {
            alert('Lỗi: ' + error.message);
        });
};

window.navigateToManageRooms = function() {
    // Thêm code điều hướng quản lý phòng nếu cần
};

window.navigateToManageAppointments = function() {
    // Thêm code điều hướng quản lý lịch hẹn nếu cần
};

window.handleNavbarRole = function() {
    const token = localStorage.getItem('jwtToken');
    if (token) {
        const payload = window.parseJwt(token);
        if (payload && payload.authorities && payload.sub) {
            const userRole = payload.authorities[0].replace('ROLE_', '');
            const username = payload.sub;
            // Hiển thị Hello + username
            const greetingText = document.getElementById('greetingText');
            if (greetingText) greetingText.textContent = `Hello ${username}`;
            const userGreeting = document.getElementById('userGreeting');
            if (userGreeting) userGreeting.style.display = 'block';
            // Ẩn Đăng nhập/Đăng ký, hiện menu Tài khoản
            const loginLink = document.getElementById('loginLink');
            const registerLink = document.getElementById('registerLink');
            const userMenu = document.getElementById('userMenu');
            if (loginLink) loginLink.style.display = 'none';
            if (registerLink) registerLink.style.display = 'none';
            if (userMenu) userMenu.style.display = 'block';
            // Hiển thị chức năng theo vai trò
            if (userRole === 'ADMIN') {
                const manageUsersLink = document.getElementById('manageUsersLink');
                const manageRoomsLink = document.getElementById('manageRoomsLink');
                const approveRoomsLink = document.getElementById('approveRoomsLink');
                if (manageUsersLink) manageUsersLink.style.display = 'block';
                if (manageRoomsLink) manageRoomsLink.style.display = 'block';
                if (approveRoomsLink) approveRoomsLink.style.display = 'block';
            } else if (userRole === 'TENANT') {
                const viewScheduleLink = document.getElementById('viewScheduleLink');
                if (viewScheduleLink) viewScheduleLink.style.display = 'block';
            } else if (userRole === 'LANDLORD') {
                const addRoomsLink = document.getElementById('addRoomsLink');
                const roomsLink = document.getElementById('roomsLink');
                const manageAppointmentsLink = document.getElementById('manageAppointmentsLink');
                if (addRoomsLink) addRoomsLink.style.display = 'block';
                if (roomsLink) roomsLink.style.display = 'block';
                if (manageAppointmentsLink) manageAppointmentsLink.style.display = 'block';
            } else {
                console.error('Vai trò không hợp lệ:', userRole);
                window.logout();
            }
        } else {
            console.error('Không thể giải mã token hoặc không có scope/sub');
            window.logout();
        }
    } else {
        // Nếu không có token, giữ giao diện mặc định (hiển thị Đăng nhập/Đăng ký)
        const loginLink = document.getElementById('loginLink');
        const registerLink = document.getElementById('registerLink');
        const userMenu = document.getElementById('userMenu');
        const userGreeting = document.getElementById('userGreeting');
        if (loginLink) loginLink.style.display = 'block';
        if (registerLink) registerLink.style.display = 'block';
        if (userMenu) userMenu.style.display = 'none';
        if (userGreeting) userGreeting.style.display = 'none';
    }
}; 