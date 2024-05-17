import api from './request';

export const getStation = () => {
    return api.get('/station');
};

export const getSearchResult = (startCity, arriveCity, date, isGD, sortType, seatType,isHide) => {
    const queryString = `?is_GD=${isGD}&sort_type=${sortType}&seat_type=${seatType}&isHide=${isHide}`;
    return api.post(`/trains/${startCity}/${arriveCity}/${date}${queryString}`);
};

export const getFoods = (tid,date,time) =>{
    return api.get(`/food/${tid}/${date}/${time}`);
};

export const getThisTicket = (userID,status) =>{
    const queryString = `?status=${status}`;
    return api.get(`/getTid/${userID}${queryString}`);
};


