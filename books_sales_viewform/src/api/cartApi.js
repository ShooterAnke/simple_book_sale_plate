import axios from '@/request.js'

export const addCart=(token,cart)=>axios.post("/api/cart/pri/addCart",cart,{
    headers:{
        'token':token
    }
})
export const getCartByUserId=(token)=>axios.get("/api/cart/pri/getCartByUserId",{
    headers:{
        'token':token
    }
})
export const deleteCart=(token,id)=>axios.get("/api/cart/pri/deleteCart",{
    headers:{
        'token':token
    },
    params:{
        'id':id
    }
})
export const updateCart=(token,cart)=>axios.post("/api/cart/pri/updateCart",cart,{
    headers:{
        'token':token
    }
})
export const stockCheck=(token,list)=>axios.post("/api/cart/pri/stockCheck",list,{
    headers:{
        'token':token
    }
})
export const cleanCart=(token)=>axios.get("/api/cart/pri/cleanCart",{
    headers:{
        'token':token
    }
})
export const addOrder=(token,list)=>axios.post("/api/cart/pri/addOrder",list,{
    headers:{
        'token':token
    }
})