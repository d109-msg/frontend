<template>
  <div class="disable" :class="{'image-container':!isDarkMode,'image-container-dark':isDarkMode}">
    <div class="image-header">
        <div :class="{'image-title':!isDarkMode,'image-title-dark':isDarkMode}">Create Feed</div>
        <div class="image-editor-cancel"
        @click="closeImage(0)"
        ></div>
    </div>
        <hr class="image-line">
        <MissonConfirm v-if="missionConfirm" 
        @close-modal="missionConfirm=false;"
        @mission-true="missionTrue"
        @mission-false="missionFalse"
        :confirmInfo="[imgData[0], missionSrc, roomId]"
        :is-dark-mode="isDarkMode" 
        />
        <WriteContent v-if="writeFlag"
        :dataInfo="{imgData,imgSrc,roomId}"
        @close-write="writeFlag=false"
        @create-feed="closeImage(1)"
        :is-dark-mode="isDarkMode" 
        />
        <div class="wrapper" v-show="!writeFlag">
            <div class="img-wrapper">
                <div class="save-wrapper">
                    <img class="first-img img-list"  @error="replaceImg">
                    <img class="second-img selected-img img-list"  @error="replaceImg" >
                    <img class="third-img selected-img img-list"  @error="replaceImg" >
                </div>
                <div class="preview-img">
                    <img class="preview" @error="replaceImg">
                </div>
            </div>
            <div class="editor-panel">
                <div :class="{'select-container':!isDarkMode, 'select-container-dark':isDarkMode}">
                        <p v-if="roomId==''">일상 게시물</p>
                        <p v-else>미션 게시물</p>
                </div>
                
                <div class="filter">
                    <label :class="{'filter-title':!isDarkMode,'filter-title-dark':isDarkMode}">Filters</label>
                    <div class="filter-box">
                        <div class="bright-info">
                            <div id="Brightness" class="brightness"></div>
                            <p :class="{'filter-value':!isDarkMode, 'filter-value-dark':isDarkMode}">{{brightness}}%</p>
                        </div>
                        <input class="bright-slider" type="range" value="100" min="0" max="200" v-model="brightness">
                        <div class="saturation-info">
                            <div id="Saturataion" class="saturation"></div>
                            <p :class="{'filter-value':!isDarkMode, 'filter-value-dark':isDarkMode}">{{saturation}}%</p>
                        </div>
                        <input class="saturation-slider" type="range" value="100" min="0" max="200" v-model="saturation">
                       
                        <div class="inversion-info">
                            <div id="Inversion" class="inversion"></div>
                            <p :class="{'filter-value':!isDarkMode, 'filter-value-dark':isDarkMode}">{{inversion}}%</p>
                        </div>
                        <input class="inversion-slider" type="range" value="0" min="0" max="100" v-model="inversion">
                        <div class="grayscale-info">
                            <div id="Grayscale" class="grayscale"></div>
                            <p :class="{'filter-value':!isDarkMode, 'filter-value-dark':isDarkMode}">{{grayscale}}%</p>
                        </div>
                        <input class="grayscale-slider" type="range" value="0" min="0" max="100" v-model="grayscale">
                    </div>
                </div>
                <div class="rotate">
                    <label :class="{'filter-title':!isDarkMode,'filter-title-dark':isDarkMode}">Rotate & Flip</label>
                    <div class="options">
                        <button id="left"><i class="fa-solid fa-rotate-left"></i></button>
                        <button id="right"><i class="fa-solid fa-rotate-right"></i></button>
                        <button id="horizontal"><i class="bx bx-reflect-vertical"></i></button>
                        <button id="vertical"><i class="bx bx-reflect-horizontal"></i></button>
                    </div>
                </div>
            </div>
        </div>
        <div class="controls" v-show="!writeFlag">
            <button class="reset-filter">Reset Filters</button>
            <div class="row" style="display: flex; flex-direction: row;">
                <input type="file" class="file-input" accept="image/*" hidden multiple>
                <button class="choose-img" style="font-weight: bold;">Choose Image</button>
                <button class="save-img" style="font-weight: bold;" @click="saveImg">NEXT</button>
            </div>
        </div>
    </div>
</template>

<script>
import btof from './base64ToFile'
import MissonConfirm from './MissonConfirm.vue'
import WriteContent from './WriteContent.vue'
import defaultImg from './Img/default_img.png'

export default {
    name: "ImageEdit",
    data(){
        return{
            missionConfirm : false,
            imgData : [],
            imgSrc : [],
            brightness : '100',
            saturation : '100',
            inversion : '0',
            grayscale : '0',
            filter : [['100','100','0','0'],['100','100','0','0'],['100','100','0','0']],
            translate : [[0,1,1],[0,1,1],[0,1,1]],
            selected : Object,
            previewImg : Object,
            rotate : 0, 
            flipHorizontal : 1, 
            flipVertical : 1,
            userRoom : ["일상 게시물","미션 게시방"], //차후 유저 방 정보 받을 시 여기에 넣으면 됨
            optionFlag : false,
            selectRoom : "",
            missionSrc : "",
            writeFlag : false,
            fileLength : 0,
            defaultImg : ''
        }
    },
    components:{
        MissonConfirm,
        WriteContent,
    },
    props:{
        roomId : String,
        isDarkMode : Boolean
    },
    methods:{
        replaceImg(e) {
                e.target.src = this.defaultImg
                if (this.defaulteImg == ''){
                    e.target.src = require(`./Img/default_img.png`);
                } 
        },
        openOptions : function(){
            this.optionFlag = !(this.optionFlag)
        },
        confirm : function(){
            if(this.roomId == ""){
                this.missionConfirm = false
                this.writeFlag = true
            } else{
                this.missionConfirm = true
            }
        },
        resetFilter : function(){
            this.brightness = '100'
            this.saturation = '100'
            this.inversion = '0'
            this.grayscale = '0'
            this.rotate = 0, this.flipHorizontal = 1, this.flipVertical = 1;
            this.applyFilters()
        },
        applyFilters : function(){
            let list = document.querySelectorAll('.img-list')
            for(let i=0; i<list.length;i++){
                    if(this.selected === list[i]){
                        this.filter[i] = [this.brightness.toString(),this.saturation.toString(),this.inversion.toString(),this.grayscale.toString()]
                        this.translate[i] = [this.rotate,this.flipHorizontal,this.flipVertical]
                        break
                    }
                }
            this.previewImg.style.filter = `brightness(${this.brightness.toString()}%) saturate(${this.saturation.toString()}%) invert(${this.inversion.toString()}%) grayscale(${this.grayscale.toString()}%)`
            this.previewImg.style.transform = `rotate(${this.rotate}deg) scale(${this.flipHorizontal}, ${this.flipVertical})`
            this.selected.style.filter = `brightness(${this.brightness.toString()}%) saturate(${this.saturation.toString()}%) invert(${this.inversion.toString()}%) grayscale(${this.grayscale.toString()}%)`
            this.selected.style.transform = `rotate(${this.rotate}deg) scale(${this.flipHorizontal}, ${this.flipVertical})`
        },

        saveImg : function(){
            this.imgData = []
            this.imgSrc = []
            let list = document.querySelectorAll('.img-list')
            console.log(list.length)
            for(let i=0; i<this.fileLength;i++){
                if(i==0 && list[i].src==""){
                    alert("입력된 이미지가 없습니다.")
                    return
                }else{
                    if(list[i].getAttribute('src')==""){
                        this.confirm()
                        return
                    }
                    const canvas = document.createElement("canvas")
                    const ctx = canvas.getContext("2d")
                    canvas.width = '416' // px 단위 빼고 그냥 숫자만 이렇게 적으면 해당 값 고정으로 사진 생성됨
                    canvas.height = '416' // px 단위 빼고 그냥 숫자만 이렇게 적으면 해당 값 고정으로 사진 생성됨
                    ctx.filter = `brightness(${this.filter[i][0]}%) saturate(${this.filter[i][1]}%) invert(${this.filter[i][2]}%) grayscale(${this.filter[i][3]}%)`
                    ctx.translate(canvas.width/2, canvas.height/2)
                    if(this.translate[i][0] != 0){
                        ctx.rotate(this.translate[i][0] * Math.PI / 180)
                    }
                    ctx.scale(this.translate[i][1], this.translate[i][2] )
                    ctx.drawImage(list[i],-canvas.width /2, -canvas.height /2, canvas.width, canvas.height )
                    let src = canvas.toDataURL();
                    if(i==0){this.missionSrc = src}
                    let fileData = btof(src,'temp.png')
                    this.imgSrc.push(src)
                    this.imgData.push(fileData)
                }
            }this.confirm()
        },

        closeImage : function(value){
            this.resetFilter()
            let list = document.querySelectorAll('.img-list')
            list.forEach(img=>{
                img.src = ""
            })
            let preview = document.querySelector('.preview')
            preview.src = ""
            if(value==0){
                this.$emit('close',0)
                // 0이면 그냥 끄기
            } else {
                this.$emit('close',1)
                // 1이면 글 작성이 완료되었다는 emit 부모 컴포넌트에서 사이트 새로고침 필요!
            }
        },
        missionTrue : function(){
            this.missionConfirm = false
            this.writeFlag = true
            alert("미션에 성공하였습니다 !!!!")
        },
        missionFalse : function(){
            this.missionConfirm=false
            alert("미션에 성공하지 못했습니다. 이미지를 수정하거나 교체 후 다시 시도하십시오.")
        }
    },
    mounted(){
        this.selectRoom = this.userRoom[0]
        this.selected = document.querySelector('.first-img')
        this.previewImg = document.querySelector('.preview')
        const fileInput = document.querySelector('.file-input'),
        chooseImgBtn = document.querySelector('.choose-img'),
        brightSlider = document.querySelector(".bright-slider"),
        saturationSlider = document.querySelector(".saturation-slider"),
        inversionSlider = document.querySelector(".inversion-slider"),
        grayscaleSlider = document.querySelector(".grayscale-slider"),
        rotateOptions = document.querySelectorAll('.rotate button'),
        resetFilterBtn = document.querySelector('.reset-filter'),
        saveImgBtn = document.querySelector('.save-img'),
        selectedImgs = document.querySelectorAll('.save-wrapper>img')
        let list = document.querySelectorAll('.img-list')

        selectedImgs.forEach(img=>{
            img.addEventListener("click",(event)=>{
                for(let i=0; i<list.length;i++){
                    if(this.selected === list[i]){
                        this.filter[i] = [this.brightness.toString(),this.saturation.toString(),this.inversion.toString(),this.grayscale.toString()]
                        this.translate[i] = [this.rotate,this.flipHorizontal,this.flipVertical]
                        break
                    }
                }
                
                list = document.querySelectorAll('.img-list')
                this.selected.classList.add('selected-img')
                event.currentTarget.classList.remove('selected-img')
                this.selected = event.currentTarget
                this.previewImg.src = this.selected.src
                this.previewImg.style.filter = this.selected.style.filter
                this.previewImg.style.transform = this.selected.style.transform
                for(let i=0; i<list.length;i++){
                    if(this.selected == list[i]){
                        this.brightness = this.filter[i][0]
                        this.saturation = this.filter[i][1]
                        this.inversion = this.filter[i][2]   
                        this.grayscale = this.filter[i][3]
                    }
                }
            })
        })



        const loadImage = ()=>{
            let files = fileInput.files
            let file = files[0] // user가 선택한 파일 1개(가장 처음)
            let saveImg = document.querySelectorAll('.save-wrapper>img')
            for(let i=0; i<saveImg.length;i++){
                saveImg[i].src = ""
            }
            if(!file || files.length>3) return; // user가 파일 선택하지 않았을때 돌아가.
            for(let i=0; i<files.length;i++){
                this.fileLength = files.length
                saveImg[i].src = URL.createObjectURL(files[i])
            }
            this.previewImg.src = URL.createObjectURL(file)
            this.previewImg.addEventListener("load",()=>{
                const container = document.querySelector(".image-container")
                if(container == null){
                    const dark = document.querySelector('.image-container-dark')
                    dark.classList.remove('disable')
                }else{
                    container.classList.remove("disable")
                }
            })
            console.log(this.imgData)
        }

        fileInput.addEventListener("change",loadImage)
        chooseImgBtn.addEventListener("click",()=>fileInput.click())


        rotateOptions.forEach(option => {
            option.addEventListener("click",()=>{
                if(option.id === "left"){
                    this.rotate -= 90  // 90도 돌림
                } else if(option.id === "right"){
                    this.rotate += 90
                } else if(option.id === "horizontal"){
                    this.flipHorizontal = this.flipHorizontal === 1 ? -1 : 1
                } else{
                    this.flipVertical = this.flipVertical === 1 ? -1 : 1
                }
                this.applyFilters()
            })
        })

        resetFilterBtn.addEventListener("click",this.resetFilter)
        brightSlider.addEventListener('input',this.applyFilters)
        saturationSlider.addEventListener('input',this.applyFilters)
        inversionSlider.addEventListener('input',this.applyFilters)
        grayscaleSlider.addEventListener('input',this.applyFilters)
    }

}
</script>

<style scoped src="./css/ImageEdit.css"> </style>
