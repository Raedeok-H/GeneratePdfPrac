##### 1. 문단(Paragraph)
- 기본 텍스트 블록을 구성하는 요소입니다.
- **정렬**: `setTextAlignment()` 메서드를 사용하여 왼쪽, 오른쪽, 중앙, 양쪽 정렬을 설정.
- **간격 설정**: `setMargin()`, `setPadding()` 메서드를 사용하여 문단의 여백 조절.
- **폰트 스타일**: `setFont()`, `setFontSize()`, `setBold()`, `setItalic()` 등을 사용하여 텍스트 스타일 지정.

##### 2. 리스트(List)
- 순서가 있는 리스트(`Ordered List`)와 순서가 없는 리스트(`Unordered List`) 생성.
- **`List` 클래스**를 사용하여 리스트를 만들고, `ListItem` 객체를 추가.
- 리스트의 **기호나 번호 스타일**을 커스터마이징 가능.

##### 3. 표(Table)
- **`Table` 클래스**를 사용하여 다양한 형태의 테이블 생성.
- 셀 병합: `setRowSpan()` 및 `setColSpan()` 메서드를 사용하여 셀 병합.
- 셀 스타일링: `setBackgroundColor()`, `setBorder()` 등을 사용하여 셀의 배경색 및 테두리 설정.

##### 4. 이미지(Image)
- **`Image` 클래스**를 사용하여 PDF에 이미지 추가.
- **크기 조정**: `scaleToFit()` 메서드를 사용하여 이미지 크기 조절.
- **위치 고정**: `setFixedPosition()` 메서드를 사용하여 특정 위치에 이미지 고정.
- **회전**: `setRotationAngle()` 메서드를 사용하여 이미지 회전 가능.

##### 5. 블록 요소(Div)
- **`Div` 클래스**는 HTML의 `div`와 유사한 컨테이너 역할.
- 여러 요소들을 하나의 `Div`로 묶어 레이아웃 관리 가능.
- `setBackgroundColor()`, `setBorder()` 등을 사용하여 블록 스타일 지정.

##### 6. 캔버스(Canvas)
- **`PdfCanvas` 클래스**를 사용하여 PDF에 직접 **그래픽 요소**를 그림.
- 선(Line), 원(Circle), 사각형(Rectangle) 등의 도형을 그리거나, 텍스트를 특정 위치에 배치 가능.
- `moveTo()`, `lineTo()`, `rectangle()` 메서드를 사용하여 그래픽 요소 그리기.

##### 7. 영역 분리(AreaBreak)
- **페이지나 컬럼**을 나눌 때 사용.
- `AreaBreak` 객체를 사용하여 **새로운 페이지**나 **새로운 컬럼**으로 내용 분리 가능.

##### 8. 스팬(Span)
- `Span` 클래스는 하나의 문단 내에서 특정 텍스트를 강조하거나 스타일 다르게 적용할 때 사용.
- 특정 텍스트에 **다른 폰트**나 **색상** 적용 가능.

##### 9. 앵커(Anchor)
- **하이퍼링크**를 생성하여 PDF 내에서 다른 페이지로 이동하거나, 외부 URL로 연결 가능.
- `setDestination()` 메서드를 사용하여 내부 페이지 이동, `setAction()` 메서드를 사용하여 외부 URL로 연결.

##### 10. 폼 필드(FormField)
- **텍스트 필드**, **버튼**, **체크박스**, **드롭다운 메뉴** 등의 폼 필드를 생성하여 사용자 입력을 받을 수 있음.
- `PdfFormField` 클래스와 관련 하위 클래스를 사용하여 필드 생성, `setValue()` 메서드로 초기값 설정 가능.

##### 11. 레이어(Layer)
- `PdfLayer` 클래스를 사용하여 레이어를 생성하고, 특정 요소를 해당 레이어에 배치 가능.
- 레이어를 통해 문서의 요소를 겹치게 하거나, 특정 조건에 따라 표시 여부를 결정할 수 있음.
