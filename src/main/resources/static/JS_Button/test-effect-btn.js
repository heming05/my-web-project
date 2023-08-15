document.getElementById('test-effect-btn').addEventListener('click', function() {
    document.getElementById('data-management-content').style.display = 'none';
    document.getElementById('test-effect-content').style.display = 'block';
    // 这里可以添加效果测试特定的逻辑

    /* 下面这段代码实现的是：点击效果测试，将数据库的数据展示到前端*/
    document.getElementById('product-table-body').style.display = 'block';

        effect_test_loadData('/effect-test-get-data'); //

});





