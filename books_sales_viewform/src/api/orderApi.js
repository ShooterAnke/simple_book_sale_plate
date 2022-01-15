import axios from '@/request.js'

export const  getOrderList=(token)=>axios.get("/api/order/pri/getOrderList",{
    headers:{
        'token':token
    }
})
export const confirmOrder=(token,order_id,user_id)=>axios.get("/api/order/pri/confirmOrder",{
    headers:{
        'token':token
    },params:{
        'order_id':order_id,
        'user_id':user_id
    }
})
export const cancelOrder=(token,order_id,user_id,list)=>axios.post("/api/order/pri/cancelOrder",list,{
    headers:{
        'token':token
    },
    params:{
        'order_id':order_id,
        'user_id':user_id
    }
})
export const getFinishCardData=(token)=>axios.get("/api/order/pri/getFinishData",{
    headers:
    {
        'token':token
    }
})
export const getCardDataByOption=(token,option1,option2,search)=>axios.get("/api/order/pri/getCardDataByOption",{
    headers:{
        'token':token
    },
    params:{
        'option1':option1,
        'option2':option2,
        'search':search
    }
})
export const confirmSend=(token,order_id)=>axios.get("/api/order/pri/confirmSend",{
    headers:{
        'token':token,
    },params:{
        'order_id':order_id
    }
})