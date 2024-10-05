document.addEventListener("DOMContentLoaded", function () {
    let addButton = document.getElementById('btn-create');
    let addForm = document.getElementById('div-create');

    addButton.addEventListener('click', function (e) {
        e.preventDefault();
        if (addForm.style.display === 'block') {
            addForm.style.display = 'none';
        } else {
            addForm.style.display = 'block';
        }
    });
});
