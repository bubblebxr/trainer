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

export const getPassengers = (id) => {
    return api.post(`/passengersInfo/${id}`);
};

export const deletePassengers = (id,identification) => {
    const queryString = `?identification=${identification}`;
    return api.delete(`/deletePassengers/${id}${queryString}`);
};

export const updatePassengers = (id, oldidentification,newname,newidentification,newphone) => {
    const queryString = `?oldidentification=${oldidentification}&newname=${newname}&newidentification=${newidentification}&newphone=${newphone}`;
    return api.post(`/updatePassengers/${id}${queryString}`);
};

export const insertPassengers = (id, name,identification,phone) => {
    const queryString = `?name=${name}&identification=${identification}&phone=${phone}`;
    return api.post(`/insertPassengers/${id}${queryString}`);
};