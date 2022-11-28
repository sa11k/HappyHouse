import { apiInstance } from "./index.js";

const api = apiInstance();

function getNotice(success, fail) {
  api.get("/notice/index").then(success).catch(fail);
}
export { getNotice };
