Week 1, Tue
========

강의하는 기분을 내기위해 교수님께서 강의실에서 원격수업을 하시기로함. ETL에 목소리가 녹음된 PPT를 업로드했다. 강의시간에는 기본적으로 PPT를 재생하고, 학생들이 질문을 하면 바로 교수님이 답변하는 플립러닝 형태로 진행하기로 함.

매 주 Lab report를 다음주까지 제출해야함. Grace period는 없다.

Team formation: 온라인 강의를 하는 기간동안에는 1인 1팀이고, 중간고사 이후부터 FPGA로 실습을 해야하는데 FPGA 수가 모자라서 2인1조 팀을 만들게될것이다.

### 강의 목표
하드웨어/소프트웨어 시스템 디자인을 하면서 어떤 문제를 겪게될지, 어떻게 해결하는지를 알아보자.

Zynq FPGA를 실제로 받아, Zynq FPGA 센서 위에서 동작하는 이미지 인식 어플리케이션을 만들것임. 하드웨어뿐만 아니라 소프트웨어도 co-design 하게된다. Verilog, Vivado 등을 사용

이미지 인식은 CNN으로 할것임. 보통은 Matrix-Matrix Multiplication으로 CNN을 구현하지만 (Convolution Lowering) 하드웨어에서는 그렇게 하는데에 이슈가 있다. 그걸 해결하는 방법을 알아볼것이다.

행렬곱을 가속하는 하드웨어도 Verilog로 설계한다. 베릴로그 짤막한 리뷰도 하게된다. Blocking이라고 제한된 메모리의 칩에서 행렬곱을 하는 테크닉이 있다 (행렬을 여러 조각으로 잘라서 연산함) Blocking 테크닉과 FPGA의 BRAM을 함께 쓰는 방법도 익힐것이다.

올바르게 동작하는 Baseline 디자인을 배우는 방법뿐만 아니라 최적화하는법도 배운다. Zero-skipping이 그중 하나. 전통적인 기법의 최적화도 한다. Direct memory access(DMA), Reduced precision, 등

우리의 디자인과 실제 Google, Apple, 삼성에서 쓰이는 신경망 가속기는 규모 면에서는 차이가 크지만, 최적화 아이디어는 견줄 수 있음.

- 우리: ~100 multipliers, 50MHz
- 구글 TPUv1: 64k multipliers, more than 500MHz
- Apple Neural Engin: 4k multiplers, more than 500MHz

### Why neural net Works?
왜 뉴럴넷이 동작하는지 항상 어려워했는데, 알고보니 실제 신경망의 동작원리와 유사한점이 많았다. 우리 시세포에 대해 알아보자.

V1 neuron이라는게 있다. 선을 감지하는 능력만 갖고있는 간단한 신경이다. https://en.wikipedia.org/wiki/Visual_cortex#Primary_visual_cortex_(V1) 얘 동작하는 원리가 퍼셉트론과 유사함.

근데 우리 시신경에는 저런 간단한 신경들의 출력을 입력으로 받는 "complex cell"이라는 뉴런이 하나 더있다. 입력중 하나라도 활성화되면 complex cell도 활성화되는, max 함수처럼 동작한다. 인공신경망의 Max Pooling Layer 역할을 하는것이고, Saliency selection이 가능해짐.

Cat Cell이라는것이 있다. 시신경 뒤의 여러 망을 거친 뒤, Inferior temporal cortex에 "고양이를 보면 무조건 활성화되는 셀", "사람을 보면 무조건 활성화되는 셀" 이런것들의 존재가 발견되었다. 인공신경망의 "output" 처럼 동작함

사람의 뇌는 새로운 물체를 보고 인식하기까지 200ms정도가 소요된다. 인공신경망은 그것보단 훨씬 빠름 (Optimized AlexNet: <100μs in GPU V100, ~1ms in Galaxy S10).

### Multi-layer perceptron (MLP)
(퍼셉트론 동작원리 설명)

퍼셉트론을 겹쳐서 Multiple Layers로 만들면 Step functions와 biases덕분에 Higher Order Function을 만들 수 있다.

### CNN
앞 레이어 입력 전체를 퍼셉트론에 넘기지 말고, 앞 레이어 입력의 일부(window)만 퍼셉트론에 넘기자.

이미지를 입력으로 하는 2D CNN 말고도, 3D CNN도 있다. Input Channel이 여러개면 3D CNN이 됨.

CNN에 대한 자세한 설명은 http://cs231n.github.io/convolutional-networks/ 참고

이 수업에선 트레이닝은 신경안쓰고, 평가(추론) 속도만 신경쓴다.

이 CNN 계산을 전통적인 방식으로 CNN을 구현하게되면 입력에 중복된 숫자가 너무 여러번 나오게된다. cuBLAS GEMM, GEMV에는 이런 문제가 있었어서 cuDNN V4에서 이런 문제를 해결했다. V5에선 Winograd convolution라는게 추가됨.

### 산업계에서의 사용
- Google TPU: 자체 실리콘 칩
- Microsoft Catapult: FPGA기반, 최근에는 실리콘칩 개발 시작함
- NVIDIA Volta GPU: GPU뿐만 아니라 Tensor Core라는 녀석을 탑재함. Tensor Core는 4x4 Fused Multiply Add에 최적화된 연산장치임

### 우리가 할 프로젝트
행렬-벡터 곱 연산장치를 직접 설계할것이다. Blocking을 기본적으로 사용하게됨.

소프트웨어로 이걸 구현하면 하드웨어 가속기 호출이 느리기때문에, 호출 오버헤드를 줄여야한다. 그러려면 블록 크기를 늘려야한다. 하지만 블록 크기를 무한정 늘릴수는 없으므로, 사용 가능한 메모리 크기와 사용 가능한 하드웨어 가속기 수에 의해 블록 크기가 결정된다.

직접 CNN을 구현하는게 첫 과제인데, 이후의 프로젝트에서도 이 구현체를 계속 사용하게된다.

Q: 하드웨어 가속기를 호출할때마다 매번 끝나기를 기다려야해서 오버헤드가 큰듯한데, 파이프라이닝을 하거나 비동기적으로 호출결과를 받아볼수는 없나요?