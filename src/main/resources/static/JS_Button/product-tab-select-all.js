document.addEventListener("DOMContentLoaded", function() {
    let allCheckbox = document.getElementById("product-select-all");
    if (allCheckbox) {
        allCheckbox.addEventListener("click", function() {
            let checkboxes = document.querySelectorAll("input[name='productCheckbox']");
            for(let checkbox of checkboxes) {
                checkbox.checked = this.checked;
            }
        });
    }
});
