document.addEventListener("DOMContentLoaded", function () {

    function calculateStatistics() {
        let totalOrders = 0;
        let totalAmount = 0;
        const serviceStats = {};
        const salesData = {};


        fetchAPI("orders", data => {
            const filteredData = filterByStatus(data, "CONFIRMED");

            // Инициализация и обработка данных
            totalOrders = filteredData.length;
            filteredData.forEach((filteredDatum) => {
                totalAmount += filteredDatum.totalPrice;

                const orderDetail = filteredDatum.orderDetails;
                serviceStats[orderDetail] = (serviceStats[orderDetail] || 0) + 1;

                const date = moment(filteredDatum.orderDate).format('YYYY-MM-DD');
                salesData[date] = (salesData[date] || 0) + filteredDatum.totalPrice;
            });


            const salesDates = Object.keys(salesData).sort();
            const salesValues = salesDates.map(date => salesData[date]);

            new Chart(salesChart, {
                type: 'bar',
                data: {
                    labels: salesDates,
                    datasets: [{
                        label: 'Продажи',
                        data: salesValues,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
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
                            }                        },
                        y: {
                            beginAtZero: true,
                            suggestedMax: Math.max(...salesValues) + 10,
                            title: {
                                display: true,
                                text: 'Сумма'
                            }                        }
                    }
                }
            });
            document.getElementById("totalOrders").textContent = totalOrders;
            document.getElementById("totalAmount").textContent = totalAmount;

            const orderStatsList = document.getElementById("serviceStats");
            for (const service in serviceStats) {
                const listItem = document.createElement("li");
                listItem.textContent = `${service}: ${serviceStats[service]} раз`;
                orderStatsList.appendChild(listItem);
            }

        });

    }
    calculateStatistics();
});
