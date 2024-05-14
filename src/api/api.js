import api from './request';

export const getStation = () => {
    return api.get('/station');
};
