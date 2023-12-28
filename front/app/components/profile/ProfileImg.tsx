import React, { useState, useRef } from 'react';
import Image from 'next/image';
import styled from 'styled-components';

export enum ProfileType {
    User = 'User', 
    Dog = 'Dog'
}

interface IProfileImgProps {
  profileType: ProfileType;
  onValueChange: (file: File) => void;
}

const ProfileImg = ({ profileType, onValueChange }: IProfileImgProps) => {

    const defaultImageSrc = profileType === ProfileType.User
    ? '/svgs/user_profile.svg'
    : '/svgs/dog_profile.svg';

    const [imageSrc, setImageSrc] = useState(defaultImageSrc );
    const fileInputRef = useRef<HTMLInputElement>(null);
  
  const handleImageClick = () => {
    fileInputRef.current?.click(); 
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];
      const fileReader = new FileReader();

      fileReader.onload = (e: ProgressEvent<FileReader>) => {
        if (typeof e.target?.result === 'string') {
          setImageSrc(e.target.result);
        }
      };

      fileReader.readAsDataURL(file);
      onValueChange(file); 
    }
  };


  return (
 <Wrap>
  <ProfileImgWrap onClick={handleImageClick}>
    <input
      type="file"
      ref={fileInputRef}
      onChange={handleFileChange}
      style={{ display: 'none' }} 
    />
    <ImgWrap >
    <Image
        src={imageSrc}
        alt="프로필 이미지"
        width={120} 
        height={120}
        objectFit="cover"
    />
    </ImgWrap>
    <EditableIcon
        src='/svgs/editable.svg'
        alt="편집"
        width={30}
        height={30}
    />
  </ProfileImgWrap>
  </Wrap>
  )
};

const Wrap = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
`
const ProfileImgWrap = styled.div`
   position: relative;
   width: 130px;
   cursor: pointer;
`
const ImgWrap = styled.div`
  width: 120px; 
  height: 120px; 
  border-radius: 50%; 
  overflow: hidden; 
`;

const EditableIcon = styled(Image)`
   position: absolute;
   top: 5px;
   right: 5px;
   border-radius: 50%;
   box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
`

export default ProfileImg;
