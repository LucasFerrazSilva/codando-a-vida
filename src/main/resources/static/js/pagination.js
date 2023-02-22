// Variables
var formFilters = document.querySelector('#filters-form');
var inputPageNumber = document.querySelector('#page-number-input');
var selectNumberOfItems = document.querySelector("#number-of-items");


//Functions
function submitFormOnChangePageSize(e) {
    inputPageNumber.value = 0;
    formFilters.submit();
}

function setPageSizeOption() {
    document.querySelectorAll("#number-of-items option").forEach(
        function(option) {
            if (option.value == selectNumberOfItems.dataset.size) {
                option.setAttribute('selected', 'selected');
            }
        }
    );
}

function addChangePageTrigger() {
    document.querySelectorAll('.pagination li:not(.disabled)').forEach(function(button) {
        button.onclick = function(e) {
            inputPageNumber.value = button.dataset.pagenumber;
            formFilters.submit();
        };
    });
}


// Calls
selectNumberOfItems.onchange = submitFormOnChangePageSize;
setPageSizeOption();
addChangePageTrigger();
