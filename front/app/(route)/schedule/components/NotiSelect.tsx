
import {useState} from 'react';
import Select from './Select';
import NotificationsNoneRoundedIcon from '@mui/icons-material/NotificationsNoneRounded';
import TextDropdown from './TextDropdown';

interface INotiSelectProps {
    onValueChange: (value: string) => void;
}

const NotiSelect = ({ onValueChange }: INotiSelectProps) => {
    const [selectedValue, setSelectedValue] = useState('');
    const options = ['없음', '정각', '5분 전', '30분 전', '1시간 전'];

    const handleSelect = (value: string) => {
        setSelectedValue(value);
        onValueChange(value);
    };
    
    return (
        <Select icon={<NotificationsNoneRoundedIcon />} label="알림" selectId="schedule-noti" selectedValue={selectedValue}
        onValueChange={handleSelect}
        >
          <TextDropdown options={options} handleSelect={handleSelect} />
        </Select>
    )
}

export default NotiSelect;