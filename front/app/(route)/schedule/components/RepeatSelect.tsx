
import {useState} from 'react';
import Select from './Select';
import ReplayRoundedIcon from '@mui/icons-material/ReplayRounded';
import TextDropdown from './TextDropdown';

interface IRepeatSelectProps {
    onValueChange: (value: string) => void;
}

const RepeatSelect = ({ onValueChange }: IRepeatSelectProps) => {
    const [selectedValue, setSelectedValue] = useState('');
    const options = ['없음', '매일', '매주', '매월', '매년'];

    const handleSelect = (value: string) => {
        setSelectedValue(value);
        onValueChange(value);
    };
    
    return (
        <Select icon={<ReplayRoundedIcon/>} label="반복" selectId="schedule-repeat" selectedValue={selectedValue}  
        onValueChange={handleSelect}
        >
            <TextDropdown options={options} handleSelect={handleSelect} />
        </Select>
    )
}

export default RepeatSelect;