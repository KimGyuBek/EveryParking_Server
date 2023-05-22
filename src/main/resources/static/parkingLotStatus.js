function draw_map() {
    let spotNum = 0;
    const parkingMap = document.getElementById('parking-map');
    for(let j=0; j < 6; j++) {
        const col = document.createElement('div');

        for (let i = 0; i < 19; i++) {
            const spot = document.createElement('div');
            const modal = document.createElement('div');
            spot.className = 'spot map-col btn btn-primary unoccupied';

            num = i+j*19;
            if ((num >= 19 && num <= 37) || (num >= 76 && num <= 94) ||
                (num >= 38 && num <= 40) || (num >= 57 && num <= 59) ||
                (num >= 54 && num <= 56) || (num >= 73 && num <= 75) ||
                (num >= 95 && num <= 97) || [16,111,9,104,2].includes(num)) {
                spot.className = 'spot';
            } else {
                spot.setAttribute('data-bs-toggle', 'modal');
                spot.setAttribute('data-bs-target', `#modal-info-${++spotNum}`)
                spot.textContent = spotNum;
                spot.id = `info-${spotNum}`;

                // 모달 요소 생성
                modal.classList.add('modal', 'fade');
                modal.id = `modal-info-${spotNum}`;
                modal.setAttribute('data-bs-backdrop', 'static');
                modal.setAttribute('data-bs-keyboard', 'false');
                modal.setAttribute('tabindex', '-1');
                modal.setAttribute('aria-labelledby', `target-${spotNum}`);
                modal.setAttribute('aria-hidden', 'true');

                // 모달 다이얼로그 생성
                const modalDialog = document.createElement('div');
                modalDialog.classList.add('modal-dialog', 'modal-dialog-centered', 'modal-dialog-scrollable');

                // 모달 콘텐츠 생성
                const modalContent = document.createElement('div');
                modalContent.classList.add('modal-content');

                // 모달 헤더 생성
                const modalHeader = document.createElement('div');
                modalHeader.classList.add('modal-header');

                // 모달 제목 생성
                const modalTitle = document.createElement('h1');
                modalTitle.classList.add('modal-title', 'fs-5');
                modalTitle.id = `target-${spotNum}`;
                modalTitle.textContent = `${spotNum}번 자리`;

                // 모달 닫기 버튼 생성
                const closeButton = document.createElement('button');
                closeButton.type = 'button';
                closeButton.classList.add('btn-close');
                closeButton.setAttribute('data-bs-dismiss', 'modal');
                closeButton.setAttribute('aria-label', 'Close');

                // 모달 본문 생성
                const modalBody = document.createElement('div');
                modalBody.classList.add('modal-body');
                modalBody.textContent = `content ${spotNum}`;

                // 모달 푸터 생성
                const modalFooter = document.createElement('div');
                modalFooter.classList.add('modal-footer');

                // 모달 이해 버튼 생성
                const understandButton = document.createElement('button');
                understandButton.type = 'button';
                understandButton.classList.add('btn', 'btn-primary');
                understandButton.setAttribute('data-bs-dismiss', 'modal');
                understandButton.textContent = '확인';

                // 모달 구조 조립
                modalHeader.appendChild(modalTitle);
                modalHeader.appendChild(closeButton);

                modalFooter.appendChild(understandButton);

                modalContent.appendChild(modalHeader);
                modalContent.appendChild(modalBody);
                modalContent.appendChild(modalFooter);

                modalDialog.appendChild(modalContent);

                modal.appendChild(modalDialog);

                // 모달을 body에 추가
                document.body.appendChild(modal);
            }
            col.appendChild(spot);
            col.appendChild(modal);
        }
        parkingMap.insertBefore(col, parkingMap.firstChild);
    }
}


draw_map();
fetch('http://43.201.95.43:8083/app/parking/map/1', {method: 'GET', headers: {'Content-Type': 'application/json'}})
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

// .fetch('http://13.125.251.219:8083/app/parking/info/1', {method: 'GET', headers: {'Content-Type': 'application/json'}})
//     .then(response => response.json())
//     .then(data => {
//         console.log(data);
//     })