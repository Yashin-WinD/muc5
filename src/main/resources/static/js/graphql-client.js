/**
 * Helper client cho GraphQL qua AJAX fetch
 */
const GRAPHQL_ENDPOINT = '/graphql';

async function graphqlFetch(query, variables = {}) {
    try {
        const response = await fetch(GRAPHQL_ENDPOINT, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                query: query,
                variables: variables
            })
        });

        const result = await response.json();
        if (result.errors && result.errors.length > 0) {
            console.error('GraphQL Errors:', result.errors);
            const msg = result.errors.map(e => e.message).join('\n');
            throw new Error(msg || 'Lỗi xử lý GraphQL');
        }

        return result.data;
    } catch (error) {
        console.error('GraphQL Fetch Error:', error);
        throw error;
    }
}

// Utility định dạng tiền VND
function formatCurrency(number) {
    if (number === null || number === undefined) return '0 ₫';
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
}

// Utility Toast thông báo
function showToast(message, type = 'success') {
    if (window.Swal) {
        Swal.fire({
            toast: true,
            position: 'top-end',
            icon: type,
            title: message,
            showConfirmButton: false,
            timer: 2500,
            timerProgressBar: true
        });
    } else {
        alert(message);
    }
}
