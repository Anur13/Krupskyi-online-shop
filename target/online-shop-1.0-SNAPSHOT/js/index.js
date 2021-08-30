const buttons = document.querySelectorAll(".btn-danger");
function getMethod(data){
return  {
 method: 'DELETE',
 headers: {
  'Content-type': 'application/json; charset=UTF-8'
 },
 body: JSON.stringify(data)
}
}
const sendDelete = (event) => {
     const tableRow = event.target.parentElement.parentElement;
     const id =  tableRow.querySelector(".id").textContent;
     fetch('http://localhost:8080/products', getMethod({id: id})).then(() => location.reload())
     }

    buttons.forEach(button => {
    button.addEventListener("click", sendDelete)
    })