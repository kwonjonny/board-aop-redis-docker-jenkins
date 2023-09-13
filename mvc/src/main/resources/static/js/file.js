//파일 보여주기
const showProducts = (arr) => {
  let str = "";
  for (const uploadFile of arr) {
    const {fileName, link, uuid} = uploadFile;
    str += `
        <li data-originName="${uuid}_${fileName}">
          <a href="http://localhost:8082/${uuid}_${fileName}" target="_blank">
            <img src="http://localhost:8082/${link}"/>
          </a>
          <p>${fileName}</p>
          <button class="btn btn-danger" onclick="javascript:removeFile(event, '${uuid}_${fileName}')">X</button>
        </li>`;
  }
  uploadUL.innerHTML += str;
}


//파일 삭제
const removeFile = (e, fileName) => {
  e.preventDefault()
  e.stopPropagation()
  const liObj = e.target.closest("li")
  //nginx 파일 삭제
  axios.delete("http://localhost:8084/removeFile/" + fileName)
  //li 삭제
  liObj.remove()
}