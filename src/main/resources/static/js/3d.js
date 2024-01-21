console.log("트리생성");
//3d 객체 설정
const scene = new THREE.Scene();//드론
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);//3d를 보여줄 카메라 설정
const renderer = new THREE.WebGLRenderer();
const container = document.querySelector(".treeDiv");//3d 객체가 담길 컨테이너
const customDiv = document.querySelector(".treeCon");
const tWidth = container.clientWidth;
const tHeight = container.clientHeight;

renderer.setSize(tWidth, tHeight);
document.getElementById('treeContainer').appendChild(renderer.domElement);

// 조명 추가
const ambientLight = new THREE.AmbientLight(0xffffff, 0.1);
scene.add(ambientLight);

const pointLight = new THREE.PointLight(0xffffff, 0.4);
pointLight.position.set(5, 5, 5);

scene.add(pointLight);

// 카메라와 렌더러 위치 설정
camera.position.x = 3;
camera.position.y = 5;
camera.position.z = 6;
renderer.setClearColor(0xffffff, 1);

// OBJ 로더를 생성하고 obj 파일을 불러오기
let object;
const loader = new THREE.OBJLoader();
loader.load('../img/3d/knx2.obj', function (loadObject) {
    object = loadObject;
    scene.add(object);
    object.position.set(-1.0,2,-1.8); // 원하는 pivot point 좌표로 설정
    object.rotation.set(0,0,0); // 원하는 회전값으로 설정
    object.scale.set(0.55, 0.55, 0.55);
});
/*

//x,y,z 축 추가
const axesHelper = new THREE.AxesHelper(5);
scene.add(axesHelper);*/
// OrbitControls를 초기화합니다.
const controls = new THREE.OrbitControls(camera, renderer.domElement);
// 카메라와 렌더러를 설정합니다.
/*  camera.position.z = 5;
  renderer.setClearColor(0xffffff, 1);*/

// 애니메이션 루프를 생성합니다.
function animate(x,y,z) {
    // requestAnimationFrame(() => animate(x, y, z));
    requestAnimationFrame(animate);
    //rotateObjectBasedOnCondition(x,y,z);
    renderer.render(scene, camera);
}
animate();


