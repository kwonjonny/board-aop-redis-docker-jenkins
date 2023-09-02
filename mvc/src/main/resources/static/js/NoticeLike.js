// Like Axios & JavaScript Code 

const urlParams = new URLSearchParams(window.location.search);
const nno = window.location.pathname.split('/').pop();

// querySelector 등록 likeCount actionLike 
const likeCountElement = document.querySelector(".likeCount");
const likeButton = document.querySelector(".actionLike");

// Like Path 
const likeLink = "http://localhost:8084/like"

// Get Like Count
const updateLikeCount = async (nno) => {
    const response = await axios.get(`${likeLink}/count/notice/${nno}`);
    likeCountElement.innerText = response.data.result;
};

// Count Like
const toggleLike = async (nno) => {
    event.preventDefault(); 
    const response = await axios.post(`${likeLink}/toggle/notice/${nno}`);
    await updateLikeCount(nno);
    toggleLikeButtonColor();
};

// Action Color Change
const toggleLikeButtonColor = () => {
    likeButton.classList.toggle("liked");
};

// Check Like
const checkLike = async (nno) => {
    const response = await axios.get(`${likeLink}/check/notice/${nno}`);
    if (response.data.result) {
        likeButton.classList.add("liked");
    } else {
        likeButton.classList.remove("liked");
    }
};

// Event Listeners
document.addEventListener("DOMContentLoaded", async () => {
    await checkLike(nno);
    await updateLikeCount(nno);
});

likeButton.addEventListener("click", async () => {
    await toggleLike(nno);
});
