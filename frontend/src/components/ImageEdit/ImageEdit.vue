<template>
  <div class="image-container disable">
        
        <div class="wrapper">
            <div class="img-wrapper">
                <div class="save-wrapper">
                    <img class="first-img" src="" alt="" style="width: 133px; height: 133px; background-repeat: no-repeat; background-size: contain;">
                </div>
                <div class="preview-img">
                    <img src="image-placeholder.svg" alt="">
                </div>
            </div>
            <div class="editor-panel">
                <div class="filter">
                    <label class="title">Filters</label>
                    <div class="options">
                        <button id="Brightness" class="active" ><i class="fa-regular fa-sun"></i></button>
                        <button id="Saturataion"><i class="fa-solid fa-circle-half-stroke"></i></button>
                        <button id="Inversion">Inversion</button>
                        <button id="Grayscale">Grayscale</button>
                    </div>
                    <div class="slider">
                        <div class="filter-info">
                            <p class="name" style="font-weight: bold;">Brightness</p>
                            <p class="value">100%</p>
                        </div>
                        <input type="range" value="100" min="0" max="200">
                    </div>
                </div>
                <div class="rotate">
                    <label class="title">Rotate & Flip</label>
                    <div class="options">
                        <button id="left"><i class="fa-solid fa-rotate-left"></i></button>
                        <button id="right"><i class="fa-solid fa-rotate-right"></i></button>
                        <button id="horizontal"><i class="bx bx-reflect-vertical"></i></button>
                        <button id="vertical"><i class="bx bx-reflect-horizontal"></i></button>
                    </div>
                </div>
            </div>
        </div>
        <div class="controls">
            <button class="reset-filter">Reset Filters</button>
            <div class="row">
                <input type="file" class="file-input" accept="image/*" hidden>
                <button class="choose-img" style="font-weight: bold;">Choose Image</button>
                <button class="save-img" style="font-weight: bold;">Save Image</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "ImageEdit",
    data(){
        return{

        }
    },
    mounted(){
        const fileInput = document.querySelector('.file-input'),
        chooseImgBtn = document.querySelector('.choose-img'),
        previewImg = document.querySelector('.preview-img img'),
        filterOptions = document.querySelectorAll(".filter button"),
        filterName = document.querySelector(".filter-info .name"),
        filterSlider = document.querySelector(".slider input"),
        filterValue = document.querySelector(".slider .value"),
        rotateOptions = document.querySelectorAll('.rotate button'),
        resetFilterBtn = document.querySelector('.reset-filter'),
        saveImgBtn = document.querySelector('.save-img')

        let brigthness = 100, saturation = 100, inversion = 0, grayscale = 0;
        let rotate = 0, flipHorizontal = 1, flipVertical = 1;


        const applyFilters = ()=>{
            previewImg.style.filter = `brightness(${brigthness}%) saturate(${saturation}%) invert(${inversion}%) grayscale(${grayscale}%)`
            previewImg.style.transform = `rotate(${rotate}deg) scale(${flipHorizontal}, ${flipVertical})`
        }


        const loadImage = ()=>{
            let file = fileInput.files[0] // user가 선택한 파일 1개(가장 처음)
            if(!file) return; // user가 파일 선택하지 않았을때 돌아가.
            previewImg.src = URL.createObjectURL(file)
            previewImg.addEventListener("load",()=>{
                const container = document.querySelector(".image-container")
                container.classList.remove("disable")
               
            })
        }

        fileInput.addEventListener("change",loadImage)
        chooseImgBtn.addEventListener("click",()=>fileInput.click())


        filterOptions.forEach(option =>{
            console.log(option)
            option.addEventListener("click",()=>{
                document.querySelector('.filter .active').classList.remove("active")
                option.classList.add("active");
            
                filterName.innerText = option.id;
                if(option.id === "Brightness"){
                    filterSlider.max = "200"
                    filterSlider.value = brigthness
                    filterValue.innerText = `${brigthness}%`
                } else if(option.id ==='Saturataion'){
                    filterSlider.max = "200"
                    filterSlider.value = saturation
                    filterValue.innerText = `${saturation}%`
                } else if(option.id ==='Inversion'){
                    filterSlider.max = "100"
                    filterSlider.value = inversion
                    filterValue.innerText = `${inversion}%`
                }else{
                    filterSlider.max = "100"
                    filterSlider.value = grayscale
                    filterValue.innerText = `${grayscale}%`
                }
            })
        })

        const updateFilter = ()=>{
            filterValue.innerText = `${filterSlider.value}%`
            const selectedFilter = document.querySelector(".filter .active")

            if(selectedFilter.id === 'Brightness'){
                brigthness = filterSlider.value;
            } else if(selectedFilter.id === 'Saturataion'){
                saturation = filterSlider.value;
            } else if(selectedFilter.id === 'Inversion'){
                inversion = filterSlider.value;
            }else{
                grayscale = filterSlider.value;
            }
            applyFilters()
        }

        rotateOptions.forEach(option => {
            option.addEventListener("click",()=>{
                if(option.id === "left"){
                    rotate -= 90  // 90도 돌림
                } else if(option.id === "right"){
                    rotate += 90
                } else if(option.id === "horizontal"){
                    flipHorizontal = flipHorizontal === 1 ? -1 : 1
                } else{
                    flipVertical = flipVertical === 1 ? -1 : 1
                }
                applyFilters()
            })
        })

        const resetFilter = ()=>{ // reset it !!
            brigthness = 100, saturation = 100, inversion = 0, grayscale = 0;
            rotate = 0, flipHorizontal = 1, flipVertical = 1;
            filterOptions[0].click(); //디폴트값으로 밝기 조절로 선택되게 하기
            applyFilters()

        }

        const saveImage = ()=>{
            const canvas = document.createElement("canvas")
            const ctx = canvas.getContext("2d")
            canvas.width = '300' // px 단위 빼고 그냥 숫자만 이렇게 적으면 해당 값 고정으로 사진 생성됨
            canvas.height = '300' // px 단위 빼고 그냥 숫자만 이렇게 적으면 해당 값 고정으로 사진 생성됨
            ctx.filter = `brightness(${brigthness}%) saturate(${saturation}%) invert(${inversion}%) grayscale(${grayscale}%)`
            ctx.translate(canvas.width/2, canvas.height/2)
            if(rotate !== 0){
                ctx.rotate(rotate * Math.PI / 180)
            }
            ctx.scale(flipHorizontal, flipVertical )
            ctx.drawImage(previewImg,-canvas.width /2, -canvas.height /2, canvas.width, canvas.height )

            const link = document.querySelector('.save-img-test')
            link.src = canvas.toDataURL();
            
        }

        resetFilterBtn.addEventListener("click",resetFilter)
        saveImgBtn.addEventListener("click",saveImage)
        filterSlider.addEventListener('input',updateFilter)
    }

}
</script>

<style scoped src="./style.css"> </style>
