function updateOrderTable() {
    const userId = getCookieValue('id');
    fetch(`http://localhost:8080/api/orders/by-user/${userId}`)
        .then((response) => response.json())
        .then(
            (data) => {
                const filteredData = filterByStatus(data, "CART");
                const tableBody = document.querySelector("#OrderTable tbody");
                tableBody.innerHTML = "";
                filteredData.forEach((order) => {
                    const row = document.createElement("tr");

                    row.innerHTML = `
              <td>${order.orderId}</td>
              <td>${order.orderDetails}</td>
              <td>${order.totalPrice}</td>
              <td>${formatDateTime(order.orderDate)}</td>
              <td>
                <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" onclick="aproveOrder(${order.orderId})">Подтвердить</button>
                <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" onclick="showDeclineOrderModal(${order.orderId})">Отменить</button>
              </td>
            `;
                    tableBody.appendChild(row);
                });
            });
}

function showDeclineOrderModal(OrderId) {
    const modal = document.getElementById("declineOrderModal");
    const confirmButton = document.getElementById("confirmDeclineOrder");
    const cancelButton = document.getElementById("cancelDeclineOrder");

    confirmButton.onclick = function () {
        modal.style.display = "none";
        declineOrder(OrderId);
    };

    cancelButton.onclick = function () {
        modal.style.display = "none";
    };

    modal.style.display = "block";
}

function declineOrder(OrderId) {
    fetch(`http://localhost:8080/api/orders/${OrderId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            status: "CANCELLED"
        }),
    }).then((response) => {
        if (response.status === 200) {
            updateOrderTable();
        }
    });
}

function aproveOrder(OrderId) {
    fetch(`http://localhost:8080/api/orders/${OrderId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            status: "OPENED"
        }),
    }).then((response) => {
        if (response.status === 200) {
            updateOrderTable();
        }
    });
}


updateOrderTable()