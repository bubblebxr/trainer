import api from './request';

export const getStation = () => {
    return api.get('/station');
};

export const getSearchResult = (startCity, arriveCity, date, isGD, sortType, seatType, isHide) => {
    const queryString = `?is_GD=${isGD}&sort_type=${sortType}&seat_type=${seatType}&isHide=${isHide}`;
    return api.post(`/trains/${startCity}/${arriveCity}/${date}${queryString}`);
};

export const getFoods = (tid, date, time) => {
    return api.get(`/food/${tid}/${date}/${time}`);
};

export const getThisTicket = (userID, status) => {
    const queryString = `?status=${status}`;
    return api.get(`/getTid/${userID}${queryString}`);
};
/*提交火车餐表单*/
export const postFoodBill = (foods, userID, tid,date,time,sum_price) => {
    var nowtime = new Date();
    return api.post(`/food/bill`,
        {
            "foods": [
                {
                    "food_name": foods.food_name,
                    "count": foods.num
                }
            ],
            "userID": userID,
            "tid":tid,
            "date":date,
            "time":time,
            "bill_time":nowtime,
            "sum_price":sum_price
        }
    );
}
/*获取火车餐订单*/
export const getFoodOrders = (userID,status)=>{
    return api.get(`/food/orders/${userID}/${status}`);
}

/*取消火车餐订单*/
export const cancelFoodOrder = (userID,oid)=>{
    return api.post(`/food/cancel/${userID}/${oid}`);
}

/*删除火车餐订单*/
export const deleteFoodOrder = (userID,oid)=>{
    return api.post(`/food/delete/${userID}/${oid}`);
}