import api from './request';

export const getStation = () => {
    return api.get('/station');
};

export const getSearchResult = (startCity, arriveCity, date, isGD, sortType, seatType, isHide) => {
    const queryString = encodeURIComponent(`?is_GD=${isGD}&sort_type=${sortType}&seat_type=${seatType}&isHide=${isHide}`);
    return api.post(`/trains/${startCity}/${arriveCity}/${date}${queryString}`);
};

export const getFoods = (userID, tid, date, time) => {
    return api.get(`/food/${userID}/${tid}/${date}/${time}`);
};

export const getThisTicket = (userID, status) => {
    const queryString = `?status=${status}`;
    return api.get(`/getTid/${userID}${queryString}`);
};
/*提交火车餐表单*/
export const postFoodBill = (foods, userID, tid, date, time, sum_price) => {
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
            "tid": tid,
            "date": date,
            "time": time,
            "bill_time": nowtime,
            "sum_price": sum_price
        }, {
        headers: {
            'Content-Type': 'application/json'
        }
    }
    );
}
/*获取火车餐订单*/
export const getFoodOrders = (userID, status) => {
    return api.get(`/food/orders/${userID}/${status}`);
}

/*取消火车餐订单*/
export const cancelFoodOrder = (userID, oid) => {
    // return api.post(`/food/cancel/${userID}/${oid}`);
    var nowtime = new Date();
    return api.post(`/food/cancel/${userID}/${oid}`,
        {
            "cancel_time": nowtime,
        }, {
        headers: {
            'Content-Type': 'application/json'
        }
    }
    );
}

/*删除火车餐订单*/
export const deleteFoodOrder = (userID, oid) => {
    return api.post(`/food/delete/${userID}/${oid}`);
}

export const getPassengers = (id) => {
    return api.post(`/passengersInfo/${id}`);
};

export const deletePassengers = (id, identification) => {
    const queryString = `?identification=${identification}`;
    return api.delete(`/deletePassengers/${id}${queryString}`);
};

export const updatePassengers = (id, oldidentification, newname, newidentification, newphone) => {
    const queryString = `?oldidentification=${oldidentification}&newname=${newname}&newidentification=${newidentification}&newphone=${newphone}`;
    return api.post(`/updatePassengers/${id}${queryString}`);
};

export const insertPassengers = (id, name, identification, phone) => {
    const queryString = `?name=${name}&identification=${identification}&phone=${phone}`;
    return api.post(`/insertPassengers/${id}${queryString}`);
};

/*提交火车票订单*/
export const postTicketBill = (persons, userID, tid, date, sum_price) => {
    var nowtime = new Date();
    return api.post(`/ticket/bill`,
        {
            "person": [
                {
                    "name": persons.name,
                    "identification": persons.identification,
                    "seat_type": persons.seat_type,
                }
            ],
            "userID": userID,
            "tid": tid,
            "date": date,
            "bill_time": nowtime,
            "sum_price": sum_price
        }, {
        headers: {
            'Content-Type': 'application/json'
        }
    }
    );
}

/*获取火车票订单*/
export const getTicketOrders = (userID, status) => {
    return api.get(`/ticket/orders/${userID}/${status}`);
}

/*取消火车票订单*/
export const cancelTicketOrder = (userID, oid) => {
    return api.post(`/ticket/cancel/${userID}/${oid}`);
}
/*获取用户所有消息*/
export const getMessage = (userID) => {
    return api.get(`/message/getAll/${userID}`)
}

/*标记消息已读*/
export const haveReadMessage = (mid) => {
    return api.post(`/message/setRead/${mid}`)
}
/*城市查询*/
export const getPlaces = () => {
    return api.get('/getPlaces');
};
/*获取酒店组*/
export const getHotel = (arrive_station, arrive_date, Ideparture_date, sort_type) => {
    const queryString = `?sort_type=${sort_type}`;
    return api.get(`/hotel/${arrive_station}/${arrive_date}/${Ideparture_date}${queryString}`);
};

/*获取酒店详细信息*/
export const getHotelDetail = (hotel_id, double_choose, big_choose, family_choose, check_in, check_out) => {
    const queryString = `?hotel_id=${encodeURIComponent(hotel_id)}&double_choose=${encodeURIComponent(double_choose)}&big_choose=${encodeURIComponent(big_choose)}&family_choose=${encodeURIComponent(family_choose)}&check_in=${encodeURIComponent(check_in)}&check_out=${encodeURIComponent(check_out)}`;
    return api.get(`/hotel_detail${queryString}`);
}

/*酒店支付*/
export const postHotelBill = (hotelid, id, checkin_time, checkout_time, room_num, room_type, customers, money) => {
    return api.post(`/hotel/bill`,
        {
            "hotel_id": hotelid,
            "id": id,
            "checkin_time": checkin_time,
            "checkout_time": checkout_time,
            "room_num": room_num,
            "room_type": room_type,
            "customers": [
                {
                    "name": customers.name,
                    "id": customers.id
                }
            ],
            "money": money
        }, {
        headers: {
            'Content-Type': 'application/json'
        }
    }

    )
}
/*酒店订单获取*/
export const getHotelOrders = (userID, status) => {
    return api.get(`/hotel/orders/${userID}/${status}`)
}
/*酒店订单取消*/
export const cancelHotelOrder = (userID, oid) => {
    return api.post(`/hotel/cancel/${userID}/${oid}`);
}

/*提交个人注册信息*/
export const postRegister = (userID, name, password, email, time) => {
    return api.post(`/register`, {
        "id": userID,
        "name": name,
        "password": password,
        "email": email,
        "time": time
    }, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

/*登陆*/
export const postLogin = (userID, password) => {
    return api.post(`/login`,
        {
            "id": userID,
            "password": password
        }, {
        headers: {
            'Content-Type': 'application/json'
        }
    }
    )
}

/*获取验证码*/
export const postCode = (email) => {
    return api.post(`/idCode/${email}`)
}

/*验证码验证结果*/
export const postCodeVeryfication = (code, email) => {
    return api.post(`/idCode/${code}/${email}`);
}

/*更新密码 */
export const updatePassword = (userID, newPassword, confirm) => {
    return api.post(`/updatepassword`,
        {
            "id": userID,
            "newpassword": newPassword,
        }, {
        headers: {
            'Content-Type': 'application/json'
        }
    }
    )
}