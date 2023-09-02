// Like Axios & JavaScript Code 

const urlParams = new URLSearchParams(window.location.search);
const bno = window.location.pathname.split('/').pop();

// querySelector 등록 likeCount actionLike 
const likeCountElement = document.querySelector(".likeCount");
const likeButton = document.querySelector(".actionLike");

// Like Path 
const likeLink = "http://localhost:8084/like"

// Get Like Count
const updateLikeCount = async (bno) => {
    const response = await axios.get(`${likeLink}/count/board/${bno}`);
    likeCountElement.innerText = response.data.result;
};

// Count Like
const toggleLike = async (bno) => {
    event.preventDefault(); 
    const response = await axios.post(`${likeLink}/toggle/board/${bno}`);
    await updateLikeCount(bno);
    toggleLikeButtonColor();
};

// Action Color Change
const toggleLikeButtonColor = () => {
    likeButton.classList.toggle("liked");
};

// Check Like
const checkLike = async (bno) => {
    const response = await axios.get(`${likeLink}/check/board/${bno}`);
    if (response.data.result) {
        likeButton.classList.add("liked");
    } else {
        likeButton.classList.remove("liked");
    }
};

// Event Listeners
document.addEventListener("DOMContentLoaded", async () => {
    await checkLike(bno);
    await updateLikeCount(bno);
});

likeButton.addEventListener("click", async () => {
    await toggleLike(bno);
});
