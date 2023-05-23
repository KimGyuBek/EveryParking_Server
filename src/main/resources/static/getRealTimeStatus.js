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

            logToastTitle.textContent = '퇴장';
            logToastTitle.style.color = 'red';
            logToastContent.textContent = `${log.carNumber}(이)가 출차하였습니다.`;
            const toast = new bootstrap.Toast(toastLive);
            toast.show();

        } else {
            EntryLogListContainer.insertBefore(addEntryLogItem(log), EntryLogListContainer.firstChild);

            logToastTitle.textContent = '입장';
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
});
socket.onclose = function () {
    console.log('WebSocket 연결이 닫혔습니다.');
};
socket.onerror = function (error) {
    console.error('WebSocket 에러:', error);
};