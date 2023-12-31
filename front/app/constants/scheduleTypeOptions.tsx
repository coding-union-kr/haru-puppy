import Image from 'next/image';
import PetsRoundedIcon from '@mui/icons-material/PetsRounded';
import WaterDropRoundedIcon from '@mui/icons-material/WaterDropRounded';
import ContentCutRoundedIcon from '@mui/icons-material/ContentCutRounded';
import ShowerRoundedIcon from '@mui/icons-material/ShowerRounded';
import CakeRoundedIcon from '@mui/icons-material/CakeRounded';
import LocalHospitalRoundedIcon from '@mui/icons-material/LocalHospitalRounded';

export const scheduleTypeOptions = [
    {label: '산책', icon: <PetsRoundedIcon style={{ color: '#C5A0F6'}}/>}, 
    {label: '밥 주기', icon:<Image src='/svgs/food.svg' alt='밥' width={20} height={20} />}, 
    {label: '물 교체', icon:<WaterDropRoundedIcon style={{ color: '#9ad8ed'}}/>}, 
    {label: '간식', icon: <Image src='/svgs/pet_supplies.svg' alt='간식' width={20} height={20} />}, 
    {label: '미용', icon:<ContentCutRoundedIcon style={{ color: '#FFC267'}}/>},
    {label: '배변', icon:<Image src='/svgs/poop.svg' alt='배변' width={20} height={20} />},
    {label: '양치', icon: <Image src='/svgs/dentistry.svg' alt='양치' width={20} height={20} />},
    {label: '목욕', icon:<ShowerRoundedIcon style={{ color: '#8295FD'}}/>},
    {label: '병원', icon:<LocalHospitalRoundedIcon style={{ color: '#81CF34'}}/>},
    {label: '생일', icon:<CakeRoundedIcon style={{ color: '#E68CB2'}}/>},
    ];
