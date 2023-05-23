import config from "./config.js";

function addReportLogItem(log) {
    // accordion-item 요소 생성

    const accordionItem = document.createElement('div');
    accordionItem.className = 'accordion-item';
    accordionItem.id = `reportLog-item-${log.reportId}`;

    // accordion-header 요소 생성
    const accordionHeader = document.createElement('h2');
    accordionHeader.className = 'accordion-header';
    accordionHeader.id = `heading-repotLog-${log.reportId}`;

    // accordion-button 요소 생성
    const accordionButton = document.createElement('button');
    accordionButton.className = 'accordion-button collapsed';
    accordionButton.type = 'button';
    accordionButton.setAttribute('data-bs-toggle', 'collapse');
    accordionButton.setAttribute('data-bs-target', `#collapse-repotLog-${log.reportId}`);
    accordionButton.setAttribute('aria-expanded', 'false');
    accordionButton.setAttribute('aria-controls', `collapse-repotLog-${log.reportId}`);

    if (log.exitTime) {
        accordionButton.style.color = "red";
    } else {
        accordionButton.style.color = "blue";
    }

    // 첫 번째 span 요소 생성 (+ 아이콘)
    const spanPlus = document.createElement('span');
    spanPlus.className = 'simple-time';
    let simpleTime = new Date(log.createdTime);
    spanPlus.textContent = `${simpleTime.getHours().toString().padStart(2, '0')}:${simpleTime.getMinutes().toString().padStart(2, '0')}`;

    // 두 번째 span 요소 생성 (사용자 ID)
    const spanUserId = document.createElement('span');
    spanUserId.textContent = log.memberId;

    // span 요소들을 accordion-button에 추가
    accordionButton.appendChild(spanPlus);
    accordionButton.appendChild(spanUserId);

    // accordion-header에 accordion-button 추가
    accordionHeader.appendChild(accordionButton);

    // accordion-collapse 요소 생성
    const accordionCollapse = document.createElement('div');
    accordionCollapse.className = 'accordion-collapse collapse';
    accordionCollapse.id = `collapse-repotLog-${log.id}`;
    accordionCollapse.setAttribute('aria-labelledby', `heading- reportLog-${log.id}`);
    accordionCollapse.setAttribute('data-bs-parent', '#reportStatus-container');

    // accordion-body 요소 생성
    const accordionBody = document.createElement('div');
    accordionBody.className = 'accordion-body';

    const reportImageDiv = document.createElement('div');
    reportImageDiv.className = 'report-image-div';

    const image = document.createElement('img');
    image.className = 'report-image';
    image.src = '...'
    image.alt = '이미지 누락';

    reportImageDiv.appendChild(image);

    const timeContainer = document.createElement('div');
    timeContainer.className = 'time-container';

    // 입장 시간 요소 생성
    const createdTimeParagraph = document.createElement('p');
    createdTimeParagraph.className = 'created-time';
    let createdTime = new Date(log.createdTime);
    createdTimeParagraph.textContent = createdTime.toLocaleString(
        'ko-KR',
        {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });

    const violationButtonContainer = document.createElement("div");
    violationButtonContainer.className = "exit-btn-container";

    const violationButton = document.createElement('button');
    violationButton.className = 'btn btn-danger btn-sm exit-btn';
    violationButton.type = 'button';
    violationButton.textContent = '위약처리';
    violationButton.setAttribute('memberId', log.memberId);

    // 위약처리 이벤트 생성
    violationButton.addEventListener('click', (e) => {
        fetch(`http://${config.ip}/api/violation`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({userId: log.memberId})
        })
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error("Failed to violate user");
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });

    violationButtonContainer.appendChild(violationButton);

    timeContainer.appendChild(createdTimeParagraph);
    timeContainer.appendChild(violationButtonContainer);

    accordionBody.appendChild(reportImageDiv);
    accordionBody.appendChild(timeContainer);

    // accordion-body를 accordion-collapse에 추가
    accordionCollapse.appendChild(accordionBody);

    // accordion-header와 accordion-collapse를 accordion-item에 추가
    accordionItem.appendChild(accordionHeader);
    accordionItem.appendChild(accordionCollapse);

    return accordionItem;
}

export default addReportLogItem;