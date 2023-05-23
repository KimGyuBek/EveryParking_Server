import config from './config.js'
import drawMap from './drawMap.js';
import addEntryLogItem from './addEntryLogItem.js';
import addReportLogItem from './addReportLogItem.js';

// 전체 출입 리스트 불러오기
const EntryLogListContainer = document.getElementById("logStatus-container");
fetch(`http://${config.ip}/api/entry-log`, {method: 'GET', headers: {'Content-Type': 'application/json'}})
    .then(response => response.json())
    .then(data => {
        data.forEach(log => {
            EntryLogListContainer.appendChild(addEntryLogItem(log));
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });

// 전체 신고 리스트 불러오기
const ReportLogListContainer = document.getElementById("reportStatus-container");
fetch(`http://${config.ip}/api/report-log`, {method: 'GET', headers: {'Content-Type': 'application/json'}})
    .then(response => response.json())
    .then(data => {
        // console.log(data)
        data.forEach(log => {
            ReportLogListContainer.appendChild(addReportLogItem(log));
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });

// 주차장 맵 그리기
drawMap();
// 주차장 좌석 현황 불러오기
fetch(`http://${config.ip}/api/map/1`, {method: 'GET', headers: {'Content-Type': 'application/json'}})
    .then(response => response.json())
    .then(data => {
        data.forEach(info => {
            // console.log(info.parkingId);
            const infoElement = document.getElementById(`info-${info.parkingId}`);
            const modalElement = document.getElementById(`modal-info-${info.parkingId}`);
            const modalHeaderTitle = modalElement.querySelector(`.modal-header-title-${info.parkingId}`);
            const modalBodyUserId = modalElement.querySelector(`.modal-body-userId`);
            const modalBodyUserName = modalElement.querySelector(`.modal-body-userName`);
            const modalBodyCarInfo = modalElement.querySelector(`.modal-body-carInfo`);
            const modalBodyTimeInfo = modalElement.querySelector(`.modal-body-timeInfo`);

            if (info.parkingStatus === 'USED') {
                infoElement.className = 'spot map-col btn btn-primary occupied';
                modalBodyUserId.textContent = `${info.details.member.userId}`;
                modalBodyUserName.textContent = `${info.details.member.userName}`;
                modalBodyCarInfo.textContent = `${info.details.carNumber}`;
                let currentTime = new Date();
                modalBodyTimeInfo.textContent = `${currentTime.getHours().toString().padStart(2, '0')}:${currentTime.getMinutes().toString().padStart(2, '0')}`
            }
        });
    })
    .catch(error => {
        console.log('Error:', error);
    })
