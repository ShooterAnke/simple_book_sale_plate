import axios from '@/request.js'

//图书接口

export const addBookApi=(token,book)=>axios.post("/api/book/pri/addBook",{
    name: book.name,
    category: book.category,
    count: book.count,
    publisher: book.publisher,
    price: book.price,
    bookNum: book.bookNum,
    coverImage: book.coverImage,
    detail:book.detail
},{headers:{
    'token':token
}})
export const addBookInfoImageApi=(token,list)=>axios.post("/api/book/pri/addBookInfoImage",list,{
    headers:{
        'token':token
    }
})
export const getPageDataApi=(token,cur,limit)=>axios.get("/api/book/pub/getPageData",{
    headers:{
        'token':token
    },
    params:{
        'cur':cur,
        'limit':limit
    }
})
export const deleteBookByIdApi=(token,id)=>axios.get("/api/book/pri/deleteBookById",{
    headers:{
        'token':token
    },
    params:{
        'id':id
    }

})
export const getAllDataByIdApi=(token,id)=>axios.get('/api/book/pub/getAllDataById',{
    headers:{
        'token':token
    },
    params:{
        'id':id
    }

})
export const updateAllDataByIdApi=(token,data)=>axios.post("/api/book/pri/updateAllDataById",data,{
    headers:{
        'token':token
    }
})
export const addCategoryApi=(token,name)=>axios.get("/api/book/pri/addCategory",{
    headers:{
        'token':token
    },
    params:{
        'name':name
    }
})
export const getCategoryListApi=(token)=>axios.get("/api/book/pri/getCategoryList",{
    headers:{
        'token':token
    }

})
export const getSearchPageDataApi=(token,option,search,cur,limit)=>axios.get("/api/book/pub/getSearchPageData",{
    headers:{
        'token':token
    },
    params:{
        'option':option,
        'cur':cur,
        'limit':limit,
        'search':search
    }
})