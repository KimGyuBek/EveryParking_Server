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
        console.log(data)
        data.forEach(log => {
            ReportLogListContainer.appendChild(addReportLogItem(log));
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });

// 주차장 정보 불러오기
drawMap();
fetch(`http://${config.ip}/app/parking/map/1`, {method: 'GET', headers: {'Content-Type': 'application/json'}})
    .then(response => response.json())
    .then(data => {
        console.log(data)
        data.parkingInfoList.forEach(info => {
            // console.log(info.parkingId);
            const infoElement = document.getElementById(`info-${info.parkingId}`);

            if (info.parkingStatus === 'USED') {
                infoElement.className = 'spot occupied';
                infoElement.addEventListener('click', () => {
                    // 모달창
                });
            }
        });
    })
    .catch(error => {
        console.log('Error:', error);
    })
