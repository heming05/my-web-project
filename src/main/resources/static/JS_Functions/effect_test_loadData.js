function effect_test_loadData(apiPath) {
    fetch(apiPath)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const table = document.getElementById('prod-table');

            // 首先，清空表格的当前数据
            while (table.rows.length > 1) {
                table.deleteRow(1);
            }

            data.forEach(item => {
                const newRow = table.insertRow();

                const selectCell = newRow.insertCell();
                const selectCheckbox = document.createElement('input');
                selectCheckbox.type = 'checkbox';
                selectCell.appendChild(selectCheckbox);

                const productNameCell = newRow.insertCell();
                productNameCell.textContent = item.product_name;

                const jobNameCell = newRow.insertCell();
                jobNameCell.textContent = item.job_name;

                const createTimeCell = newRow.insertCell();
                createTimeCell.textContent = item.create_time;

                // ... 这里可以继续添加其他单元格 ...
            });
        })
        .catch(error => {
            console.log('Fetch error: ', error);
        });
}
