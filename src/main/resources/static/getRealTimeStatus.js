import config from './config.js'
import addEntryLogItem from './addEntryLogItem.js';
import addReportLogItem from './addReportLogItem.js';

const EntryLogListContainer = document.getElementById("logStatus-container");
const ReportLogListContainer = document.getElementById("reportStatus-container");

// 소켓 연결
let socket, stompClient;
socket = new WebSocket(`ws://${config.ip}/ws/log`);
stompClient = Stomp.over(socket);
stompClient.connect({}, function() {
    // 실시간 출입 기록
    stompClient.subscribe('/topic/entry-log', function (response) {
        let log = JSON.parse(response.body);
        const existLogItem = document.getElementById(`entryLog-item-${log.id}`) ?? null;
        const toastLive = document.getElementById('liveToast');
        const logToastTitle = document.getElementById('logToast-title');
        const logToastContent = document.getElementById('logToast-content');
        if (existLogItem !== null) {
            const updatedExitTime = existLogItem.querySelector('.exit-time');
            updatedExitTime.textContent =
                new Date(log.exitTime).toLocaleString(
                    'ko-KR',
                    {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit',
                        hour: '2-digit',
                        minute: '2-digit',
                        second: '2-digit'
                    }
                );
            const accordionButton = existLogItem.querySelector('.accordion-button');
            accordionButton.style.color = "red";

            logToastTitle.textContent = '반납';
            logToastTitle.style.color = 'red';
            logToastContent.textContent = `${log.carNumber}(이)가 출차하였습니다.`;
            const toast = new bootstrap.Toast(toastLive);
            toast.show();

        } else {
            EntryLogListContainer.insertBefore(addEntryLogItem(log), EntryLogListContainer.firstChild);

            logToastTitle.textContent = '승인';
            logToastTitle.style.color = 'blue';
            logToastContent.textContent = `${log.carNumber}(이)가 입차하였습니다.`;
            const toast = new bootstrap.Toast(toastLive);
            toast.show();
        }
    });

    // 실시간 신고 현황
    stompClient.subscribe('/topic/report-log', function (response) {
        let log = JSON.parse(response.body);
    });


    // 실시간 좌석 현황
    stompClient.subscribe('/topic/parking-status', function (response) {
        let info = JSON.parse(response.body);
        console.log(info)

        const infoElement = document.getElementById(`info-${info.parkingId}`);
        const modalElement = document.getElementById(`modal-info-${info.parkingId}`);
        const modalHeaderTitle = modalElement.querySelector(`.modal-header-title-${info.parkingId}`);
        const modalBodyUserId = modalElement.querySelector(`.modal-body-userId`);
        const modalBodyUserName = modalElement.querySelector(`.modal-body-userName`);
        const modalBodyCarInfo = modalElement.querySelector(`.modal-body-carInfo`);
        const modalBodyTimeInfo = modalElement.querySelector(`.modal-body-timeInfo`);

        const toastLive = document.getElementById('liveToast');
        const logToastTitle = document.getElementById('logToast-title');
        const logToastContent = document.getElementById('logToast-content');

        if (info.parkingStatus === 'AVAILABLE') {
            infoElement.className = 'spot map-col btn btn-primary unoccupied';
            modalBodyUserId.textContent = "";
            modalBodyUserName.textContent = "";
            modalBodyCarInfo.textContent = "";
            modalBodyTimeInfo.textContent = "";

        } else if (info.parkingStatus === 'USED') {
            infoElement.className = 'spot map-col btn btn-primary occupied';
            modalBodyUserId.textContent = `${info.details.member.userId}`;
            modalBodyUserName.textContent = `${info.details.member.userName}`;
            modalBodyCarInfo.textContent = `${info.details.carNumber}`;
            let currentTime = new Date();
            modalBodyTimeInfo.textContent = `${currentTime.getHours().toString().padStart(2, '0')}:${currentTime.getMinutes().toString().padStart(2, '0')}`

            logToastTitle.textContent = '배정';
            logToastTitle.style.color = 'red';
            logToastContent.textContent = `${info.details.carNumber}(이)가 ${info.parkingId}번에 배정되었습니다.`;
            const toast = new bootstrap.Toast(toastLive);
            toast.show();
        }
    });
});
socket.onclose = function () {
    console.log('WebSocket 연결이 닫혔습니다.');
};
socket.onerror = function (error) {
    console.error('WebSocket 에러:', error);
};