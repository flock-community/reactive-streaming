import {useState} from "react";

export const useAddWordToList = () => {
    const [words, setWords] = useState([])

    const addWordToList = (word) => {
        setWords(prevState => {
            const newArray = [word].concat(prevState)
            if (newArray.length > 15) {
                newArray.pop()
            }

            return newArray
        })
    }

    return [words, addWordToList]
}
const mapToRadialChartFormat = (distributionDTO) => {
    return Object.entries(distributionDTO.wordDistribution).map(([word, value]) => {
        return {angle: value, label: word, x:word, y:value}
    });
};

export const parseNewWordTrendDistribution = distributionDTO => {
    const mapToRadialChartFormat1 = mapToRadialChartFormat(distributionDTO);
    // console.log(mapToRadialChartFormat1);
    return {
        total: distributionDTO.wordTotal,
        distribution: mapToRadialChartFormat1
    }
};

export const emptyDistribution = {
    total: 0,
    distribution: [{
        angle: 0.1,
        label: 'Flock.',
        x: 'Flock.',
        y: 0.1
    }]
};