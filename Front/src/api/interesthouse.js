import { apiInstance } from "./index.js";

const api = apiInstance();

async function listinteresthouse(user_id, success, fail) {
  await api.get(`/interesthouse/${user_id}`).then(success).catch(fail);
}

async function registinteresthouse(house, success, fail) {
  await api.get(`/interesthouse/regist/${house.user_id}/${house.housedeal_no}`).then(success).catch(fail);
}

async function deleteinteresthouse(no, success, fail) {
  await api.delete(`${no}`).then(success).catch(fail);
}

// function logout(success, fail)

export { listinteresthouse, registinteresthouse, deleteinteresthouse };
