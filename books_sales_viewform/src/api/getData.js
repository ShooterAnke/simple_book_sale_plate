import axios from '@/request.js'
/* import qs from 'qs' */

//登陆接口
export const loginApi = (username, password) => axios.post("/api/user/pub/login", {
    'username': username,
    'password': password
})
export const registerApi=(username,password,phone)=>axios.post("/api/user/pub/register",{
    'username':username,
    'password':password,
    'phone':phone
})
export const checkCaptchaApi=(captcha)=>axios.get("/api/captcha/pub/check",{
    params:{
        'captcha':captcha
    }
})
export const getCaptchaImage=()=>axios.get("/api/captcha/pub/get_captcha",{responseType:'blob'})

export const findByUsernameApi=(token)=>axios.get("/api/user/pri/findByUsername",{
    headers:{
        'token':token
    }
})

export const getUserPageDataApi=(token,cur,limit)=>axios.get("/api/user/pri/getPageData",{
    headers:{
        'token':token
    },
    params:{
        'cur':cur,
        'limit':limit
    }
})

export const deleteByUsername=(token,username)=>axios.get("/api/user/pri/deleteByUsername",{
    headers:{
        'token':token
    },
    params:{
        'username':username
    }
})
export const updateByUsername=(token,user)=>axios.post("/api/user/pri/updateByUsername",{
     'username':user.username,
     'phone':user.phone,
     'headImage':user.headImage,
     'name':user.name,
     'power':user.power,
     'address':user.address
},{
    headers:{
        'token':token,
     },
})
export const searchPage=(token,option,username,cur,limit)=>axios.get("/api/user/pri/searchPageData",{
    headers:{
        'token':token
    },
    params:{
        'option':option,
        'username':username,
        'cur':cur,
        'limit':limit
    }

})
export const updatePassword=(token,username,password)=>axios.post("/api/user/pri/updatePassword",{
    'username':username,
    'password':password
},{
    headers:{
        'token':token,
        
    }
})
export const updatePhoneApi=(token,username,phone)=>axios.get("/api/user/pri/updatePhone",{
    headers:{
        'token':token,
    },
    params:{
        'username':username,
        'phone':phone
    }
})
export const updateAddressApi=(token,username,address)=>axios.get("/api/user/pri/updateAddress",{
    headers:{
        'token':token,
    },
    params:{
        'username':username,
        'address':address,
    }
    
})
export const updateNameApi=(token,username,name)=>axios.get("/api/user/pri/updateName",{
    headers:{
        'token':token,
    }
    ,params:{
        'username':username,
        'name':name
    }
})