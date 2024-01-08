export interface ScheduleItem {
    scheduleId: number;
    scheduleType: string;
    mates: {
        userId: number, user_img: string
    }[];
    scheduleDate?: string;
    reservedDate?: string;
    time: string;
    repeatId?: string | null;
    active: boolean;
}
