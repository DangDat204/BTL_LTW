async function fetchWithAuth(url, options = {}) {
    const token = localStorage.getItem('jwtToken');

    // Kiểm tra token
    if (!token) {
        alert('Vui lòng đăng nhập lại!');
        window.location.href = '/user/login';
        return null;
    }

    // Kiểm tra token hết hạn
    const payload = parseJwt(token);
    if (!payload || (payload.exp && payload.exp < Math.floor(Date.now() / 1000))) {
        alert('Phiên đăng nhập đã hết hạn!');
        logout();
        return null;
    }

    // Thêm header Authorization
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
        ...options.headers
    };

    try {
        const response = await fetch(url, {
            ...options,
            headers
        });

        if (response.status === 401) {
            alert('Phiên đăng nhập không hợp lệ!');
            logout();
            return null;
        } else if (response.status === 403) {
            alert('Bạn không có quyền truy cập!');
            return null;
        }

        return response;
    } catch (error) {
        console.error('Error fetching API:', error);
        alert('Không thể kết nối đến server!');
        return null;
    }
}