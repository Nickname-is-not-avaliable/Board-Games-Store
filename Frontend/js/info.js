document.addEventListener("DOMContentLoaded", function () {

    function calculateStatistics() {
        let totalOrders = 0;
        let totalAmount = 0;
        const serviceStats = {};
        const salesData = {};

        fetchAPI("orders", data => {
            const filteredData = filterByStatus(data, "CONFIRMED");
            totalOrders = filteredData.length;

            for (const filteredDatum of filteredData) {
                totalAmount += filteredDatum.totalPrice;

                const orderDetail = filteredDatum.orderDetails;
                if (orderDetail in serviceStats) {
                    serviceStats[orderDetail]++;
                } else {
                    serviceStats[orderDetail] = 1;
                }

                const date = moment(filteredDatum.orderDate);
                const day = date.format('YYYY-MM-DD');
                if (salesData[day]) {
                    salesData[day] += filteredDatum.totalPrice;
                } else {
                    salesData[day] = filteredDatum.totalPrice;
                }
            }

            document.getElementById("totalOrders").textContent = totalOrders;
            document.getElementById("totalAmount").textContent = totalAmount;

            const orderStatsList = document.getElementById("serviceStats");
            for (const service in serviceStats) {
                const listItem = document.createElement("li");
                listItem.textContent = `${service}: ${serviceStats[service]} раз`;
                orderStatsList.appendChild(listItem);
            }

            const salesChart = document.getElementById("salesChart");
            const salesDates = Object.keys(salesData);
            salesDates.sort();
            const salesValues = Object.values(salesData);

            new Chart(salesChart, {
                type: 'line',
                data: {
                    labels: salesDates, datasets: [{
                        label: 'Продажи',
                        data: salesValues, backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        x: {
                            type: 'time',
                            time: {
                                parser: 'YYYY-MM-DD',
                                unit: 'day',
                                displayFormats: {
                                    day: 'MMM D, YYYY'
                                },
                            },
                            title: {
                                display: true,
                                text: 'Дата'
                            }
                        },
                        y: {
                            beginAtZero: true,
                            suggestedMax: Math.max(...salesValues) + 10,
                            title: {
                                display: true,
                                text: 'Сумма'
                            }
                        }
                    }
                }
            });
        });
    }
    calculateStatistics();
});
