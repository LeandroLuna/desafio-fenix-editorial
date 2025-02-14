import { Lesson } from "./lesson.model";

export interface Course {
    id: number;
    name: string;
    description: string;
    category: string;
    duration: number;
    image: string;
    lessons: Lesson[];
}